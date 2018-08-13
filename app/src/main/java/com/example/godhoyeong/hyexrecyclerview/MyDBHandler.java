package com.example.godhoyeong.hyexrecyclerview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBHandler {
//    private final String TAG = "MyDBHandler";
//
//    SQLiteOpenHelper mHelper = null;
//    SQLiteDatabase mDB = null;
//
//    public MyDBHandler(Context context, String name) {
//        mHelper = new MyHelper(context, name, null, 1);
//    }
//
//    public static MyDBHandler open(Context context, String name) {
//        return new MyDBHandler(context, name);
//    }
//
//    public Cursor select()
//    {
//        mDB = mHelper.getReadableDatabase();
//        Cursor c = mDB.query("mysocial", null, null, null, null, null, null);
//        return c;
//    }
//
//    public void insert(String title, String name) {
//
//        Log.d(TAG, "insert");
//
//        mDB = mHelper.getWritableDatabase();
//
//        ContentValues value = new ContentValues();
//        value.put("title", title);
//        value.put("name", name);
//
//        mDB.insert("mysocial", null, value);
//
//    }
//
//    public void delete(String name)
//    {
//        Log.d(TAG, "delete");
//        mDB = mHelper.getWritableDatabase();
//        mDB.delete("mysocial", "title=?", new String[]{name});
//    }
//
//    public void close() {
//        mHelper.close();
//    }
}
