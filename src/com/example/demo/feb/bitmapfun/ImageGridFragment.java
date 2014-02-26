
package com.example.demo.feb.bitmapfun;

import java.lang.reflect.TypeVariable;

import com.example.demo.R;
import com.example.demo.feb.bitmapfun.utils.ImageFetcher;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class ImageGridFragment extends Fragment {

    private int mImageThumbSize;
    private int mImageThumbSpacing;
    private ImageAdapter mAdapter;
    private ImageFetcher mImageFetcher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        mImageThumbSpacing = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);

        mAdapter = new ImageAdapter(getActivity());
//        mImageFetcher = new ImageFetcher(getActivity(), mImageThumbSize);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView text = new TextView(getActivity());
        text.setText("ggood");
        return text;
    }

    private class ImageAdapter extends BaseAdapter {
        private final Context mContext;
        private int mItemHeight = 0;
        private int mNumColumns = 0;
        private GridView.LayoutParams mImageViewLayoutParams;
        private int mActionBarHeight = 0;

        public ImageAdapter(Context context) {
            mContext = context;
            mImageViewLayoutParams = new GridView.LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);
            // Calculate ActionBar height
            TypedValue tv = new TypedValue();
            if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                mActionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context
                        .getResources().getDisplayMetrics());
            }
        }

        @Override
        public int getCount() {
//            return Images;
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
        
        // 当知道宽度值，可以匹配的设置高度
        public void setItemHeight(int height) {
            if(mItemHeight == height) {
                return;
            }
            mItemHeight  = height;
            mImageViewLayoutParams =
                    new GridView.LayoutParams(LayoutParams.MATCH_PARENT, mItemHeight);
//            mImageFetcher.setImageSize(height);
            notifyDataSetChanged();
        }
        
        public int getNumColumns() {
            return mNumColumns;
        }
        
        public void setNumColumns(int numColumns) {
            mNumColumns = numColumns;
        }
    }
}
