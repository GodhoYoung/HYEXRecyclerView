package com.example.godhoyeong.hyexrecyclerview;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {
    public static void insertFakeData(SQLiteDatabase db){
        if(db == null){
            return;
        }
        //create a list of fake guests
        List<ContentValues> list = new ArrayList<>();

        ContentValues cv = new ContentValues();
        cv.put(MyContract.MyEntry.COLUMN_USER_NAME,"aa");
        cv.put(MyContract.MyEntry.COLUMN_TEXT, "aaaaa");
        list.add(cv);

        cv = new ContentValues();
        cv.put(MyContract.MyEntry.COLUMN_USER_NAME, "Tim");
        cv.put(MyContract.MyEntry.COLUMN_TEXT, "bbbbb");
        list.add(cv);

        cv = new ContentValues();
        cv.put(MyContract.MyEntry.COLUMN_USER_NAME, "Jessica");
        cv.put(MyContract.MyEntry.COLUMN_TEXT, "ccccc");
        list.add(cv);

        cv = new ContentValues();
        cv.put(MyContract.MyEntry.COLUMN_USER_NAME, "Larry");
        cv.put(MyContract.MyEntry.COLUMN_TEXT, "ddddd");
        list.add(cv);

        cv = new ContentValues();
        cv.put(MyContract.MyEntry.COLUMN_USER_NAME, "Kim");
        cv.put(MyContract.MyEntry.COLUMN_TEXT, "eeeee");
        list.add(cv);

        //insert all guests in one transaction
        try
        {
            db.beginTransaction();
            //clear the table first
            db.delete (MyContract.MyEntry.TABLE_NAME,null,null);
            //go through the list and add one by one
            for(ContentValues c:list){
                db.insert(MyContract.MyEntry.TABLE_NAME, null, c);
            }
            db.setTransactionSuccessful();
        }
        catch (SQLException e) {
            //too bad :(
        }
        finally
        {
            db.endTransaction();
        }

    }
}
