package com.example.godhoyeong.hyexrecyclerview;

import android.provider.BaseColumns;

public class MyContract {
    public static final class MyEntry implements BaseColumns{
        public static final String TABLE_NAME = "mysocial";
        public static final String COLUMN_USER_NAME = "userName";
        public static final String COLUMN_TEXT = "postText";
    }
}
