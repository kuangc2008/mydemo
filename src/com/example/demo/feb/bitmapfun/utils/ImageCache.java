
package com.example.demo.feb.bitmapfun.utils;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.example.demo.BuildConfig;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.LruCache;
import android.util.Log;

/**
 * 这个类，一般和ImageWorker一起使用，做磁盘和内存的缓存。<br/>
 * 用getInstance来得到示例，并通过ImageWork.addImageCache来添加进缓存
 */
public class ImageCache {
    private static final String TAG = "ImageCache";
    private static final int DEFAULT_MEM_CACHE_SIZE = 1024 * 5; // 5MB
    private static final boolean DEFAULT_MEM_CACHE_ENABLE= true;
    private static final boolean DEFAULT_INIT_DISK_CACHE_ON_CREATE = false;
    
    private ImageCacheParams mCacheParams;
    private Set<SoftReference<Bitmap>> mReusableBitmaps;
    private LruCache<String, BitmapDrawable> mMemoryCache;
    
    private final Object mDiskCacheLock = new Object();
    
    ImageCache(ImageCacheParams cacheParams) {
        init(cacheParams);
    }

    private void init(ImageCacheParams cacheParams) {
        mCacheParams = cacheParams;
        
        // set up the memrory cache
        if(mCacheParams.memoryCacheEnable) {
            if(BuildConfig.DEBUG) {
                Log.d(TAG, "Memory cache create ( size = " + mCacheParams.memCacheSize + " )");
            }
            //3.0或之上，创建一系列的可用的图片，可填充到BitmapFactory.Options的inBitmap属性中
            //注意：SoftReference实际上不会很有效率，因为垃圾回收机制会很快清理掉Soft/WeakReferences.
            //所以更好的方法还是使用强引用。
            //但是这需要平衡LruCache和内存使用量，也需知道图片将占用内存的大小
            //从3.0到JellyBean之间，LruCache占用空间是固定的，但是到kitkat是一个上限
            if(Utils.hasHoneycomb()) {
                mReusableBitmaps = Collections.synchronizedSet(new HashSet<SoftReference<Bitmap>>());
            }
            mMemoryCache = new LruCache<String, BitmapDrawable>(mCacheParams.memCacheSize) {

                /**
                 * Notify the removed entry that is no longer being cached
                 */
                @Override
                protected void entryRemoved(boolean evicted, String key, BitmapDrawable oldValue,
                        BitmapDrawable newValue) {
                    if(RecyclingBitmapDrawable.class.isInstance(oldValue)) {
                        // the removed entry is a recycling drawable, so notify it
                        // that it has been removed from the memory cache
                        ((RecyclingBitmapDrawable)oldValue).setIsCache(false);
                    } else {
                        // the removed entry is a standard BitmapDrawable
                        if(Utils.hasHoneycomb()) {
                            // we're runing on Honeycomb or later, so add the bitmap
                            // to a softReference set for possible use with inBitmap later
                            mReusableBitmaps.add(new SoftReference<Bitmap>(oldValue.getBitmap()));
                        }
                    }
                }

                @Override
                protected int sizeOf(String key, BitmapDrawable value) {
                    final int bitmapSize = getBitmapSize(value) / 1024;
                    return bitmapSize == 0? 1: bitmapSize;
                }
                
            };
        }
        // By default the disk cache is not initialized here as it should be initialized
        // on a separate thread due to disk access.
        if(cacheParams.initDiskCacheOnCreate) {
            // set up disk cache
            initDiskCache();
        }
    }

    /**
     * Initializes the disk cache. Note that this includes disk access to this shold not be
     * executed on the main/UI thread. by default an ImageCache does not initialize the disk
     * cache when it is created, instead you should call initDiskCache() to initialize it on a
     * background thread.
     */
    private void initDiskCache() {
        //set up the disk cache
        synchronized (mDiskCacheLock) {
//            if(mDiskCache)
        }
    }

    public static ImageCache getInstance(FragmentManager fm, ImageCacheParams cacheParams) {
        final RetainFragment mRetainFragment = findOrCreateRetainFragment(fm);
        ImageCache imageCache = (ImageCache) mRetainFragment.getObject();
        if(imageCache == null) {
            imageCache = new ImageCache(cacheParams);
            mRetainFragment.setObject(imageCache);
        }
        return imageCache;
    }

    /**
     * A holder class 包含 cache parameters
     */
    public static class ImageCacheParams {
        public File diskCacheDir;
        public int memCacheSize = DEFAULT_MEM_CACHE_SIZE;
        public boolean memoryCacheEnable = DEFAULT_MEM_CACHE_ENABLE;
        public boolean initDiskCacheOnCreate = DEFAULT_INIT_DISK_CACHE_ON_CREATE;

        /**
         * create a set of image cache parameters that can be provideed to 
         * {@link ImageCache#getInstance(FragmentManager, ImageCacheParams)} or
         * ImageWorker
         * @param context
         * @param diskCacheDirecotryName A unique subdirecotry name that will be append
         *   to the applicaton cache directory. Usually "cache" or "images" is sufficient.
         */
        public ImageCacheParams(Context context, String diskCacheDirecotryName) {
            diskCacheDir = getDiskCacheDir(context, diskCacheDirecotryName);
        }

        /**
         * memCacheSize以kb存储，而不是以b，因为最终要传给LruCache，而该类构造方法需要int
         */
        public void setMemCacheSizePercent(float percent) {
            if(percent < 0.01f || percent >0.8f) {
                throw new IllegalArgumentException("setMemCacheSizePercent - percent must be "
                        + "between 0.01 and 0.8 (inclusive)");
            }
            memCacheSize = Math.round(percent * Runtime.getRuntime().maxMemory() / 1024);
        }
    }
    
    public static int getBitmapSize(BitmapDrawable value) {
        Bitmap bitmap = value.getBitmap();
        if(Utils.hasHoneycombMR1()) {
            return bitmap.getByteCount();
        }
        //pre HC-MR1
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    public static File getDiskCacheDir(Context context, String uniqueName) {
        boolean sdcardMounted = Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
        String cachePath = null;
        if (sdcardMounted) {
            if (isExternalStorageRemovable()) {
                cachePath = context.getCacheDir().getPath();
            } else {
                cachePath = getExternalCacheDir(context).getPath();
            }
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    public static File getExternalCacheDir(Context context) {
        if (Utils.hasFroyo()) {
            return context.getExternalCacheDir();
        }
        // before froyo we need to construct to external cache dir ourselves
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }

    @TargetApi(VERSION_CODES.GINGERBREAD)
    public static boolean isExternalStorageRemovable() {
        if (Utils.hasGingerbread()) {
            return Environment.isExternalStorageRemovable();
        }
        return true;
    }

    public static class RetainFragment extends Fragment {
        private Object mObject;
        public RetainFragment() {}
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // make sure this fragment is retained over a configuration change
            setRetainInstance(true);
        }
        public void setObject(Object object) {
            mObject = object;
        }
        public Object getObject() {
            return mObject;
        }
    }

    /**
     * Locate an existing instance of Fragment or if not found, create and add it 
     * using FragmentManager
     */
    private static RetainFragment findOrCreateRetainFragment(FragmentManager fm) {
        RetainFragment mRetainFragment = (RetainFragment) fm.findFragmentByTag(TAG);
        if(mRetainFragment == null) {
            mRetainFragment = new RetainFragment();
            fm.beginTransaction().add(mRetainFragment, TAG).commitAllowingStateLoss();
        }
        return mRetainFragment;
    }
}
