package com.example.godhoyeong.hyexrecyclerview;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.godhoyeong.hyexrecyclerview.recyclerview.PostAdapter;
import com.example.godhoyeong.hyexrecyclerview.server.ServerActivity;

public class AddActivity extends AppCompatActivity {
    String aa;
    TextView tvUserName;
    EditText etTitle;
    private SQLiteDatabase mDb;
    AlertDialog dialog;
    String stTitle;
    String stName;
    Cursor mCursor;
    private PostAdapter mAdapter;
    int REQUEST_ACT = 111;

;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etTitle = (EditText) findViewById(R.id.tv_PostText_add);
        tvUserName = (TextView) findViewById(R.id.tv_UserName_add);

        Intent intent = getIntent();
        tvUserName.setText(intent.getStringExtra("userID"));

        Button btnComplete = (Button)findViewById(R.id.btnComplete);
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(tvUserName.getText().toString() != null && etTitle.getText().toString() != null) {
                    stTitle = etTitle.getText().toString();
                    stName = tvUserName.getText().toString();
                    Intent intent = new Intent(AddActivity.this, MainActivity.class);
                    intent.putExtra("title",stTitle);
                    intent.putExtra("name",stName);
                    Intent intent1 = new Intent(AddActivity.this, ServerActivity.class);
                    intent1.putExtra("title",stTitle);
                    intent1.putExtra("name",stName);
                    setResult(RESULT_OK,intent1);
                    setResult(RESULT_OK,intent);
                    finish();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
                    dialog= builder.setMessage("name, context 가 비어있는지 확인해주세요")
                            .setNegativeButton("취소", null)
                            .create();
                    dialog.show();
                    return;
                }




            }
        });




    }







}
