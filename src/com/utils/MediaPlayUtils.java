package com.utils;

import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

/**
 * Created by kuangcheng on 2014/10/18.
 */
public class MediaPlayUtils {
    private MediaPlayer mPlayer = null;

    private static MediaPlayUtils mUtils = null;

    public static MediaPlayUtils getInstance() {
        if(mUtils == null) {
            mUtils = new MediaPlayUtils();
        }
        return mUtils;
    }

    private MediaPlayUtils() {
        mPlayer = new MediaPlayer();
    }

    public MediaPlayer playAudio(String uri, final MediaPlayer.OnPreparedListener listener) {
        if(mPlayer == null) {
            mPlayer = new MediaPlayer();
        }
        mPlayer.reset();
        try {
            mPlayer.setDataSource(GlobalUtils.mContext, Uri.parse(uri));
            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mPlayer.start();
                    listener.onPrepared(mp);
                }
            });
            mPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mPlayer;
    }

    public MediaPlayer getmPlayer() {
        return mPlayer;
    }
}
