package com.example.yinxiang.audio;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kuangcheng on 2014/10/19.
 */
public class AudioDBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 2;
    public static final String DB_TABLE = "my_audio";
    public static final String OBJECT_ID = "objectId";
    public static final String _ID = "_id";
    public static final String URL = "url";
    public static final String TITLE = "title";
    public static final String DESC = "description";
    public static final String IS_DOWNLODED = "is_download";
    public static final String FILE_PATH= "file_path";
    public static final String FROM = "com_from";
    public static final String YINXIANGURI = "yinxianguri";
    public static final String TYPE = "type";

    public AudioDBHelper(Context context) {
        super(context, "hehe.db", null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            db.execSQL("CREATE TABLE " + DB_TABLE + "(" + _ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT," + URL
                    + " TEXT, "
                    + OBJECT_ID + " TEXT, "
                    + TITLE + " TEXT, "
                    + DESC + " TEXT, "
                    + IS_DOWNLODED + " BOOLEAN,"
                    + FILE_PATH + " TEXT"
                    +    ");");

            db.execSQL("ALTER TABLE my_audio\n" +
                    "   ADD COLUMN " + FROM +  " INTEGER DEFAULT 0");
            db.execSQL("ALTER TABLE my_audio\n" +
                    "   ADD COLUMN " + YINXIANGURI + "  TEXT");
            db.execSQL("ALTER TABLE my_audio\n" +
                    "   ADD COLUMN " + TYPE +"   TEXT ");
        } catch (SQLException ex) {
            throw ex;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < 2) {
            db.execSQL("ALTER TABLE my_audio\n" +
                    "   ADD COLUMN " + FROM +  " INTEGER DEFAULT 0");
            db.execSQL("ALTER TABLE my_audio\n" +
                    "   ADD COLUMN " + YINXIANGURI + "  TEXT");
            db.execSQL("ALTER TABLE my_audio\n" +
                    "   ADD COLUMN " + TYPE +"   TEXT ");
        }
    }

    public static final String[] ALL_COlUMN = {
            _ID, URL, TITLE, DESC, IS_DOWNLODED, FILE_PATH, OBJECT_ID, FROM ,YINXIANGURI,TYPE
    };
    public static final int ID_INDEX =0;
    public static final int URL_INDEX =1;
    public static final int TITLE_INDEX =2;
    public static final int DESC_INDEX =3;
    public static final int IS_DOWNLODED_INDEX =4;
    public static final int FILE_PATH_INDEX =5;
    public static final int OBJECT_ID_INDEX =6;
    public static final int FROM_INDEX =7;
    public static final int YINXIANGURI_INDEX =8;
    public static final int TYPE_INDEX = 9;

}
