package com.example.godhoyeong.hyexrecyclerview.server;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.godhoyeong.hyexrecyclerview.MyContract;
import com.example.godhoyeong.hyexrecyclerview.MyHelper;
import com.example.godhoyeong.hyexrecyclerview.R;
import com.example.godhoyeong.hyexrecyclerview.recyclerview.PostAdapter;


public class ServerActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;
    private PostAdapter mAdapter;
    private String stName = "";
    private String stTitle= "";
    String userID;
    String userText;
    int REQUEST_ACT = 111;
    Cursor cursor;

    RecyclerView rv_ListServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        rv_ListServer = (RecyclerView)findViewById(R.id.rv_ListServer);


        Button btnServer = (Button)findViewById(R.id.btn_server_add);
        btnServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userID = stName;
                userText = stTitle;
                Toast.makeText(getApplicationContext(), "클릭됨", Toast.LENGTH_SHORT).show();



            }
        });




        rv_ListServer.setLayoutManager(new LinearLayoutManager(ServerActivity.this));
//

//
//        PostAdapter adapter = new PostAdapter(this, listItem);
        MyHelper dbHelper = new MyHelper(ServerActivity.this);
        // 데이터를 DB에 채우기 위함
        mDb = dbHelper.getWritableDatabase();
        // 자동적으로 다섯명의 손님을 DB에 추가
//        TestUtil.insertFakeData(mDb);
        //커서에 결과를 저장
         cursor = getAllSocial();

        // 데이터를 표시할 커서를 위한 어댑터 생성
        mAdapter = new PostAdapter(getApplicationContext(), cursor,ServerActivity.this);

        rv_ListServer.setAdapter(mAdapter);

        Button btnBack = (Button)findViewById(R.id.btn_serverback);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private Cursor getAllSocial() {
        // 두번째 파라미터 (Projection array)는 여러 열들 중에서 출력하고 싶은 것만 선택해서 출력할 수 있게 한다.
        // 모든 열을 출력하고 싶을 때는 null 을 입력한다.
        return mDb.query(
                MyContract.MyEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }
    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;
        @Override
        protected void onPreExecute(){


        }
        @Override
        protected String doInBackground(Void... voids){
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String result){
            Intent intent1 = getIntent();
            stName = intent1.getStringExtra("name");
            stTitle = intent1.getStringExtra("title");

        }
    }

}
