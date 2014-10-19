package com.example.yinxiang.audio;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Binder;

import java.io.File;

/**
 * Created by kuangcheng on 2014/10/19.
 */
public class AudioProvider extends ContentProvider {
    private SQLiteOpenHelper mOpenHelper = null;
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String AUTHORITY = "com.example.yinxiang.audio";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/audios");

    private static final int MY_AUDIOS = 1;
    private static final int MY_AUDIO_ID = 2;
    static {
        sURIMatcher.addURI(AUTHORITY, "audios", MY_AUDIOS);
        sURIMatcher.addURI(AUTHORITY, "audio/#", MY_AUDIO_ID);
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new AudioDBHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = null;
        try {
            db = mOpenHelper.getReadableDatabase();
        } catch (Exception e) {
            return null;
        }

        int match = sURIMatcher.match(uri);
        if(match == MY_AUDIO_ID) {
            String longid = uri.getPathSegments().get(1);
            selection = selection + "(" + AudioDBHelper._ID +  longid + ")";
        }
        Cursor ret = db.query(AudioDBHelper.DB_TABLE, projection, selection,
                selectionArgs, null,
                null, sortOrder);
        if (ret != null) {
            ret.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return ret;
    }


    private static final String AUDIO_LIST_TYPE = "vnd.android.cursor.dir/audio";
    private static final String AUDIO_TYPE = "vnd.android.cursor.item/audio";
    @Override
    public String getType(Uri uri) {
        int match = sURIMatcher.match(uri);
        switch (match) {
            case MY_AUDIOS:
                return AUDIO_LIST_TYPE;
            case MY_AUDIO_ID: {
                return AUDIO_TYPE;
            }
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long rowID = db.insert(AudioDBHelper.DB_TABLE, null, values);

        if (rowID != -1) {
            Uri uriToNotify = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(uriToNotify, null);
        } else {
            return null;
        }
        return ContentUris.withAppendedId(CONTENT_URI, rowID);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count = 0;
        int match = sURIMatcher.match(uri);
        switch (match) {
            case MY_AUDIOS:
            case MY_AUDIO_ID:
                count = db.delete(AudioDBHelper.DB_TABLE, selection, selectionArgs);
        }
        if (match == MY_AUDIO_ID) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
