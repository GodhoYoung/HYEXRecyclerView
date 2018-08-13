package com.example.godhoyeong.hyexrecyclerview.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.godhoyeong.hyexrecyclerview.R;

public class CameraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        ImageView ivPost = (ImageView)findViewById(R.id.iv_post);

//        Bundle extras = getIntent().getExtras();
//        Bitmap imageBitmap = (Bitmap) extras.get("data");
        Intent intent = getIntent();
        Uri photoUri = intent.getData();
        ivPost.setImageURI(photoUri);
    }
}
