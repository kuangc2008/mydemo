package com.example.yinxiang.audio;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.demo.R;
import com.example.yinxiang.YinXiangFile;
import com.example.yinxiang.YinXiangNote;
import com.example.yinxiang.YinXinagDownloadMng;
import com.utils.FilePathHelper;
import com.utils.MediaPlayUtils;
import com.utils.PreferenceUtils;
import com.utils.TimeUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.MemoryHandler;

public class YinXiangAudio extends Activity implements View.OnClickListener {
    YinXiangAudioAdapter mAdapter = null;
    private List<YinXiangAudioNote> mList = null;
    private LayoutInflater mInfater = null;
    private ListView mListView = null;
    private ProgressBar mProgressBar;
    private MediaPlayer mMediaPlayer;
    private TextView durationTV;
    private TextView progressTV;
    private LinearLayout mBottomView = null;
    public static YinXiangAudio INSTANCE = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInfater = LayoutInflater.from(this);
        initViews();
        INSTANCE = this;
        YinXinagDownloadMng.getInstance().downloadAudio(new Response.Listener<YinXiangAudioResult>() {
            @Override
            public void onResponse(YinXiangAudioResult response) {
                mList = response.getResults();
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "下载");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0: {
                for(YinXiangAudioNote note : mList) {
                    final YinXiangAudioNote fianlNote = note;
                    if(note.getFilePath() == null) {
                        if(note.getFilePath() == null || !new File(note.getFilePath()).exists()) {
                            Uri downloaduri = Uri.parse(note.getUri().getUrl());
                            final String name = downloaduri.getLastPathSegment();
                            AsyncTask task = new AsyncTask<Void, Void, Boolean>() {
                                @Override
                                protected Boolean doInBackground(Void... params) {
                                    String savePath = FilePathHelper.getAudioPath(name);
                                    boolean isSuccess = YinXinagDownloadMng.getInstance().downloadFile(fianlNote.getUri().getUrl(),
                                            savePath);
                                    if(isSuccess) {
                                        Uri uri = ContentUris.withAppendedId(AudioProvider.CONTENT_URI, fianlNote.getId());
                                        ContentValues values = new ContentValues();
                                        values.put(AudioDBHelper.FILE_PATH, savePath);
                                        getBaseContext().getContentResolver().update(uri, values, null, null);
                                        fianlNote.setFilePath(savePath);
                                    }
                                    return isSuccess;
                                }

                                @Override
                                protected void onPostExecute(Boolean aVoid) {
                                    mAdapter.notifyDataSetChanged();
                                }
                            };
                            task.execute((Void[])null);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "failture",  Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }
        return true;
    }

    private void initViews() {
        RelativeLayout rl = new RelativeLayout(this);
        //1 添加listView
        mListView = new ListView(this);
        mListView.setCacheColorHint(0);
        mAdapter = new YinXiangAudioAdapter();
        mListView.setAdapter(mAdapter);
        rl.addView(mListView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //添加时间1
        progressTV = new TextView(this);
        progressTV.setText("45:21");
        progressTV.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams llLp1 = new LinearLayout.LayoutParams((int) (50*getResources().getDisplayMetrics().scaledDensity), ViewGroup.LayoutParams.WRAP_CONTENT);

        //2 廷加progressbar
        mProgressBar = new ProgressBar(this, null, android.R.style.Widget_ProgressBar_Horizontal);
        mProgressBar.setIndeterminate(false);
        mProgressBar.setProgressDrawable(getResources().getDrawable(android.R.drawable.progress_horizontal));
        mProgressBar.setMinimumHeight((int) (50 * getResources().getDisplayMetrics().scaledDensity));
        mBottomView = new LinearLayout(this);
        mBottomView.setOrientation(LinearLayout.VERTICAL);
        LinearLayout line2 = new LinearLayout(this);
        LayoutInflater.from(this).inflate(R.layout.play_pause, mBottomView);
        mBottomView.addView(line2);
        LinearLayout.LayoutParams llLp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        llLp.weight = 1;
        llLp.gravity = Gravity.CENTER_VERTICAL;
        line2.addView(progressTV,  llLp1);
        line2.addView(mProgressBar,  llLp);
        //3 添加时间
        durationTV = new TextView(this);
        durationTV.setText("45:21");
        durationTV.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams llLp2 = new LinearLayout.LayoutParams((int) (50*getResources().getDisplayMetrics().scaledDensity), ViewGroup.LayoutParams.WRAP_CONTENT);
        line2.addView(durationTV,  llLp2);

        mBottomView.setVisibility(View.GONE);
        RelativeLayout.LayoutParams params = new  RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(30, 0, 30, 30);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rl.addView(mBottomView,params);
        setContentView(rl, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                YinXiangAudioNote note = mAdapter.getItem(position);
                String url = null;
                if(note.getFilePath() != null && new File(note.getFilePath()).exists()) {
                    url = Uri.fromFile(new File(note.getFilePath())).toString();
                } else {
                    url = note.getUri().getUrl();
                }
                mMediaPlayer = MediaPlayUtils.getInstance().playAudio(url, new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mProgressBar.setMax(mp.getDuration());
                        durationTV.setText(TimeUtils.getTimeStr(mp.getDuration()));
                        mhandler.sendEmptyMessage(MSG_REFRESH_PROGRESS);
                    }
                });
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mBottomView.setVisibility(View.GONE);
                    }
                });
            }
        });

        findViewById(R.id.start).setOnClickListener(this);
        findViewById(R.id.pre).setOnClickListener(this);
        findViewById(R.id.next).setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        INSTANCE = null;
    }

    private static final int MSG_REFRESH_PROGRESS = 0;
    private android.os.Handler mhandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_REFRESH_PROGRESS:
                    if(mMediaPlayer.isPlaying()) {
                        mBottomView.setVisibility(View.VISIBLE);
                        mProgressBar.setProgress(mMediaPlayer.getCurrentPosition());
                        progressTV.setText(TimeUtils.getTimeStr(mMediaPlayer.getCurrentPosition()));
                        sendEmptyMessageDelayed(MSG_REFRESH_PROGRESS, 500);
                    }

                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pre:
                if(mMediaPlayer != null) {
                    mMediaPlayer.seekTo(mMediaPlayer.getCurrentPosition() - 5 * 1000);
                }
                break;
            case R.id.next:
                if(mMediaPlayer != null) {
                    mMediaPlayer.seekTo(mMediaPlayer.getCurrentPosition() + 5 * 1000);
                }
                break;
            case R.id.start:
                if(mMediaPlayer != null) {
                   if(mMediaPlayer.isPlaying()) {
                       mMediaPlayer.pause();
                   } else {
                       mMediaPlayer.start();
                       mhandler.sendEmptyMessage(MSG_REFRESH_PROGRESS);
                   }
                }
                break;
        }
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
                mHolder.mImageView = (ImageView) convertView.findViewById(R.id.imageview);
                convertView.setTag(mHolder);
            } else {
                mHolder = (ViewHolder) convertView.getTag();
            }

            YinXiangAudioNote note = getItem(position);
            mHolder.textLine1.setText(note.getTitle().replace("$$", "\n"));
            mHolder.textLine2.setText(note.getDescription().replace("$$", "\n"));
            if(note.getFilePath() != null && new File(note.getFilePath()).exists()) {
                mHolder.mImageView.setVisibility(View.VISIBLE);
            } else {
                mHolder.mImageView.setVisibility(View.GONE);
            }
            return convertView;
        }

    }
    private static class ViewHolder {
        TextView textLine1;
        TextView textLine2;
        ImageView mImageView;
    }


}
