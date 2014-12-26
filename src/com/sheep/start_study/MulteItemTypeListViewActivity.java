package com.sheep.start_study;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.demo.R;

import java.util.Random;

/**
 * Created by kuangcheng on 2014/12/26.
 */
public class MulteItemTypeListViewActivity extends Activity {
    private ListView mListView = null;
    private MultiItemTypeAdapter mAdapter = null;
    private SparseArray<Integer> ids = new SparseArray<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListView = new ListView(this);
        mAdapter = new MultiItemTypeAdapter();
        mListView.setAdapter(mAdapter);
        setContentView(mListView);
    }

    private class MultiItemTypeAdapter extends BaseAdapter {

        @Override
        public int getViewTypeCount() {
            return 3;
        }

        @Override
        public int getItemViewType(int position) {
            if(ids.get(position) == null) {
                Random r = new Random();
                int randomInt = r.nextInt(3);
                ids.put(position, randomInt);
                Log.w("kcc", "getItemViewType->" + randomInt + "  4position->" + position);
                return randomInt;
            } else {
                return ids.get(position);
            }
        }

        @Override
        public int getCount() {
            return 10000;
        }

        @Override
        public String getItem(int position) {
            return "hehe" + position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = null;
            switch (getItemViewType(position)) {
                case 0:
                    v = updateView4Type0(position, convertView, parent);
                    break;
                case 1:
                    v = updateView4Type1(position, convertView, parent);
                    break;
                case 2:
                    v = updateView4Type2(position, convertView, parent);
                    break;
            }

            return v;
        }


        private View updateView4Type0(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = new TextView(MulteItemTypeListViewActivity.this);
                ViewHolder1 vh1 = new ViewHolder1();
                vh1.mTV = (TextView) convertView;
                convertView.setTag(vh1);
            } else {
                ViewHolder1 vh1 = (ViewHolder1) convertView.getTag();
                convertView = vh1.mTV;
            }
            TextView tv = (TextView) convertView;
            tv.setText(getItem(position));
            return convertView;
        }

        private View updateView4Type1(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = new Button(MulteItemTypeListViewActivity.this);
                ViewHolder2 vh2 = new ViewHolder2();
                vh2.mButton = (Button) convertView;
                convertView.setTag(vh2);
            } else {
                ViewHolder2 vh1 = (ViewHolder2) convertView.getTag();
                convertView = vh1.mButton;
            }
            TextView button = (Button) convertView;
            button.setText(getItem(position));
            return convertView;
        }


        private View updateView4Type2(int position, View convertView, ViewGroup parent) {
            ViewHolder3 vh3 = null;
            if(convertView == null) {
                convertView = LayoutInflater.from(MulteItemTypeListViewActivity.this).
                        inflate(android.R.layout.simple_list_item_2, null);
                vh3 = new ViewHolder3();
                vh3.mtv1 = (TextView) convertView.findViewById(android.R.id.text1);
                vh3.mtv2 = (TextView) convertView.findViewById(android.R.id.text2);
                convertView.setTag(vh3);
            } else {
                vh3 = (ViewHolder3) convertView.getTag();
            }
            vh3.mtv1.setText(getItem(position));
            vh3.mtv2.setText(getItem(position));
            return convertView;
        }
    }


    static class ViewHolder1 {
        public TextView mTV;
    }

    static class ViewHolder2 {
        public Button mButton;
    }
    static class ViewHolder3 {
        public TextView mtv1;
        public TextView mtv2;
    }

}
