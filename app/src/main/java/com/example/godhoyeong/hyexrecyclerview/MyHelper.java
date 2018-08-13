package com.example.godhoyeong.hyexrecyclerview;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.godhoyeong.hyexrecyclerview.MyContract.MyEntry;

public class MyHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "social.db";
    private static final int DATABASE_VERSION = 1;

    public MyHelper(Context context) {
        super(context,  DATABASE_NAME, null, DATABASE_VERSION);
    }

    // 실제 DB가 생성된다.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MYSOCIAL_TABLE = "CREATE TABLE " + MyEntry.TABLE_NAME + " (" +
                    MyEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MyEntry.COLUMN_USER_NAME + " TEXT NOT NULL, " +
                MyEntry.COLUMN_TEXT + " TEXT NOT NULL); ";

        // 쿼리 실행
        sqLiteDatabase.execSQL(SQL_CREATE_MYSOCIAL_TABLE);
    }

    // DB 스키마가 최근 것을 반영하게 해준다.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // 버전이 바뀌면 예전 버전의 테이블을 삭제 (나중에 ALTER 문으로 대체)
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MyEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
