package com.example.yinxiang.audio;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.example.demo.R;
import com.example.yinxiang.YinXiangNote;
import com.example.yinxiang.YinXinagDownloadMng;

import java.util.List;

public class YinXiangAudio extends Activity {
    YinXiangAudioAdapter mAdapter = null;
    private List<YinXiangAudioNote> mList = null;
    private LayoutInflater mInfater = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInfater = LayoutInflater.from(this);
        ListView lv = new ListView(this);
        lv.setCacheColorHint(0);
        mAdapter = new YinXiangAudioAdapter();
        lv.setAdapter(mAdapter);
        setContentView(lv, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                YinXiangNote note = mAdapter.getItem(position);
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(note.url));
//                startActivity(intent);
            }
        });
        YinXinagDownloadMng.getInstance().downloadAudio(new Response.Listener<YinXiangAudioResult>() {
            @Override
            public void onResponse(YinXiangAudioResult response) {
                mList = response.getResults();
                mAdapter.notifyDataSetChanged();
            }
        });
    }



    class YinXiangAudioAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList == null ? 0 : mList.size();
        }

        @Override
        public YinXiangAudioNote getItem(int position) {
            if(mList == null) {
                return null;
            }
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder mHolder = null;
            if(convertView == null) {
                mHolder = new ViewHolder();
               convertView = mInfater.inflate(R.layout.two_line_icon, null);
                mHolder.textLine1 = (TextView) convertView.findViewById(android.R.id.text1);
                mHolder.textLine2 = (TextView) convertView.findViewById(android.R.id.text2);
                convertView.setTag(mHolder);
            } else {
                mHolder = (ViewHolder) convertView.getTag();
            }

            YinXiangAudioNote note = getItem(position);
            mHolder.textLine1.setText(note.getTitle());
            mHolder.textLine2.setText(note.getDescription());
            return convertView;
        }

    }
    private static class ViewHolder {
        TextView textLine1;
        TextView textLine2;
    }


}
