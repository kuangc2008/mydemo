package com.example.volley;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.demo.R;
import com.kc.toolbox.GsonRequest;
import com.kc.toolbox.MyImageLoader;
import com.last201409.UrlUtils;


public class LVolley2_CusImageView extends Activity{
    private RequestQueue rq = null;
    private ListView mListView = null;
    private VolleyImagesAdapter mAdapter = null;
    private Images mImages = null;
    private MyImageLoader mImageLoader = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        rq = Volley.newRequestQueue(this);
        mImageLoader = new MyImageLoader(rq, new LruBitmapCache(10000));
        GsonRequest<Images> gr = new GsonRequest<Images>(Request.Method.GET, UrlUtils.IMAGE_URL, Images.class, null, new Response.Listener<Images>() {
            @Override
            public void onResponse(Images response) {
                if(response != null && response.images != null) {
                    mImages = response;
                    mAdapter.setImages(response);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("kcc", "onErrorResponse");
            }
        });
        gr.setHeader("X-AVOSCloud-Application-Id", "418dgirlkcy5pqph1tiuts5pxhdhjz4s3rft986mvevo73hi");
        gr.setHeader("X-AVOSCloud-Application-Key", "r7cj4qp0t84ak240hdr9j62uqw6cv5qcl7og753c5lcp3pyp");
        rq.add(gr);
    }

    @Override
    protected void onStop() {
        super.onStop();
        rq.stop();
    }

    private void initViews() {
        RelativeLayout ll = new RelativeLayout(this);
        mListView = new ListView(this);
        mListView.setCacheColorHint(0);
        mAdapter = new VolleyImagesAdapter(null);
        mListView.setAdapter(mAdapter);
        TextView mEmptyView = new TextView(this);
        mEmptyView.setText("正在加载图片");
        mListView.setEmptyView(mEmptyView);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(UrlUtils.FULL_LAYOUT_PARAMS);
        ll.addView(mListView, lp);
        lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.addRule(RelativeLayout.CENTER_IN_PARENT,  RelativeLayout.TRUE);
        ll.addView(mEmptyView, lp);
        setContentView(ll, UrlUtils.FULL_LAYOUT_PARAMS);
    }

    class VolleyImagesAdapter extends BaseAdapter {
        private Images mImages = null;

        VolleyImagesAdapter(Images images) {
            this.mImages = images;
        }

        public void setImages(Images images) {
            this.mImages = images;
        }

        @Override
        public int getCount() {
            if(mImages != null && mImages.images != null) {
                return mImages.images.size();
            }
            return 0;
        }

        @Override
        public String getItem(int position) {
            if(mImages != null && mImages.images != null) {
                return mImages.images.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final NetworkImageView iv;
            if(convertView == null) {
                iv = getInflatedView();
            } else {
                iv = (NetworkImageView) convertView;
            }
            String url = getItem(position);

            iv.setDefaultImageResId(R.drawable.number_blue_3);
            iv.setErrorImageResId(R.drawable.app_sample_code);
            iv.setImageUrl(url, mImageLoader);

            return iv;
        }

        private NetworkImageView getInflatedView() {
            NetworkImageView iv = new NetworkImageView(LVolley2_CusImageView.this);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setLayoutParams(new ListView.LayoutParams(200, 200));
            return  iv;
        }

    }
    public class LruBitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache{

        public LruBitmapCache(int maxSize) {
            super(maxSize);
        }

        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getRowBytes() * value.getHeight()/1024;
        }

        @Override
        public Bitmap getBitmap(String url) {
            return get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            put(url, bitmap);
        }
    }
}
