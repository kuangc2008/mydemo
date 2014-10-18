package com.example.yinxiang;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.demo.R;
import com.utils.DialogUtils;
import com.utils.PreferenceUtils;


import java.util.List;

/**
 * Created by kuangcheng on 2014/9/29.
 */
public class YinXiangMain extends Activity {
    List<YinXiangNote> mNotes = null;
    private YinXiangAdapter mAdapter = null;
    private YinXinagDownloadMng  donwloadMng = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNotes =YinXiangManager.getRandomNoteList(this);

        ListView lv = new ListView(this);
        lv.setCacheColorHint(0);
        mAdapter = new YinXiangAdapter();
        lv.setAdapter(mAdapter);
        setContentView(lv, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                YinXiangNote note = mAdapter.getItem(position);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(note.url));
                startActivity(intent);
            }
        });
        donwloadMng = YinXinagDownloadMng.getInstance();
        donwloadMng.downloadNote();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "刷新").setIcon(R.drawable.menu_container_refresh_d).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == 0) {
            if(PreferenceUtils.getInstance().getRefreshTime() >= 1) {
                Toast.makeText(this, "刷新次数已到极限了", Toast.LENGTH_SHORT).show();
                return true;
            }

            final Dialog dialog = DialogUtils.createRefreshDialog(this, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    mNotes =YinXiangManager.getRandomNoteList(YinXiangMain.this, true);
                    mAdapter.notifyDataSetChanged();
                }
            });
            dialog.show();
        }
        return true;
    };

    private class YinXiangAdapter extends BaseAdapter {
        private LayoutInflater inflater = null;

        private YinXiangAdapter() {
            inflater = LayoutInflater.from(YinXiangMain.this);
        }

        @Override
        public int getCount() {
            return mNotes.size();
        }

        @Override
        public YinXiangNote getItem(int position) {
            return mNotes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = null;
            if(convertView == null) {
                view = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, null);
            } else {
                view = (TextView) convertView;
            }
            YinXiangNote note = getItem(position);
            view.setText(note.title);
            return view;
        }
    }
}
