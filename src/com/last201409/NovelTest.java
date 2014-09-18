package com.last201409;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by kuangcheng on 2014/9/18.
 */
public class NovelTest extends Activity{

    private String[]  mUriStr = new String[] {
            "qnovel://reader/bid=20156655&name=天才&author=大主宰&total=100&chapterid=2&from=onebox",
            "qnovel://detail/bid=8182253048507660294&from=onebox",
            "qnovel://home/from=onebox",
            "qnovel://search/query=大主宰&type=&title=大主宰&from=onebox"
    };

    private String[]  mNameStr = new String[] {
            "小说阅读页",
            "小说详情页",
            "小说书城页",
            "小说列表页"
    };

    private MyAdapter mAdapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView lv = new ListView(this);
        mAdapter = new MyAdapter();
        lv.setAdapter(mAdapter);
        setContentView(lv);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mUriStr.length;
        }

        @Override
        public String getItem(int position) {
            return mNameStr[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getBaseContext()).inflate(android.R.layout.simple_list_item_1, null);
            TextView mtv = (TextView) view.findViewById(android.R.id.text1);
            mtv.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setData(Uri.parse(mUriStr[position]));
                    startActivity(i);
                }
            });
            mtv.setText(getItem(position));
            return view;
        }
    }
}
