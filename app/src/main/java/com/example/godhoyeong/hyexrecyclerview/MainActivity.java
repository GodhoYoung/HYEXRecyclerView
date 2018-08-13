package com.example.godhoyeong.hyexrecyclerview;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.godhoyeong.hyexrecyclerview.fragment.FragActivity;
import com.example.godhoyeong.hyexrecyclerview.management.ManageActivity;
import com.example.godhoyeong.hyexrecyclerview.model.PostItem;
import com.example.godhoyeong.hyexrecyclerview.recyclerview.PostAdapter;
import com.example.godhoyeong.hyexrecyclerview.server.ServerActivity;
import com.example.godhoyeong.hyexrecyclerview.server.ServerDeleteRequest;
import com.example.godhoyeong.hyexrecyclerview.server.ServerRequest;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    AlertDialog dialog;

    private SQLiteDatabase mDb;
    private PostAdapter mAdapter;
    private String stName = "";
    private String stTitle= "";
    int REQUEST_ACT = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = getIntent();
        stName = intent.getStringExtra("userID").toString();
//        if(handler == null){
//            handler= MyDBHandler.open(MainActivity.this, "mysocial.db");
//        }




        Button btnServer = (Button)findViewById(R.id.btnServer);
        btnServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, ServerActivity.class);
                intent2.putExtra("userID",stName);
                intent2.putExtra("userTitle",stTitle);
                MainActivity.this.startActivity(intent2);
            }
        });
        final Button btnAdd = (Button)findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("추가 하시겠습니까?");
                    builder.setPositiveButton("확인 ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(MainActivity.this, AddActivity.class);
                            intent.putExtra("userID",stName);
                            startActivityForResult(intent, REQUEST_ACT);
                        }
                    });
//                        .setPositiveButton("확인", null)
//                            .setNegativeButton("취소",null)
//                            .create();
//                    dialog.show();
                    builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    });
                    dialog = builder.create();
                    dialog.show();


            }

        });
        Button btnFrag = (Button)findViewById(R.id.btn_frag);
        btnFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, FragActivity.class);
                startActivity(intent2);
            }
        });

        Button btnManage = (Button)findViewById(R.id.btn_manage);
        if(!stName.equals("admin")){
            btnManage.setEnabled(false);
            btnManage.setVisibility(View.GONE);
        }

        btnManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgroundTask().execute();
            }
        });

//        ArrayList<PostItem> listItem = new ArrayList<>();


        final RecyclerView rv_List = (RecyclerView)findViewById(R.id.rv_List);
        rv_List.setLayoutManager(new LinearLayoutManager(this));
//

//
//        PostAdapter adapter = new PostAdapter(this, listItem);
       MyHelper dbHelper = new MyHelper(this);
        // 데이터를 DB에 채우기 위함
        mDb = dbHelper.getWritableDatabase();
        // 자동적으로 다섯명의 손님을 DB에 추가
//        TestUtil.insertFakeData(mDb);
        //커서에 결과를 저장
        final Cursor cursor = getAllSocial();

        // 데이터를 표시할 커서를 위한 어댑터 생성
        mAdapter = new PostAdapter(this, cursor, this);

        rv_List.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            // 사용하지 않는다.
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, final int direction) {
                // 스와이프된 아이템의 아이디를 가져온다.
                final Intent intent = getIntent();
                stName = intent.getStringExtra("userID").toString();
                // DB 에서 해당 아이디를 가진 레코드를 삭제한다.
                final long id = (long) viewHolder.itemView.getTag();
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage("SOCIALTEST 서버 데이터 삭제 성공").setPositiveButton("확인",null)
                                        .create().show();

                                removeUser(id);
                                mAdapter.swapCursor(getAllSocial());
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage("SOCIALTEST 서버 실패").setNegativeButton("취소",null)
                                        .create().show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };

                ServerDeleteRequest deleteRequest = new ServerDeleteRequest(stName,stTitle,listener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(deleteRequest);
            }
        }).attachToRecyclerView(rv_List);  //리사이클러뷰에 itemTouchHelper 를 붙인다.




    }


    //-----------------------------------------------------------------------------------------------회원 삭제

//    Response.Listener<String> responseListener = new Response.Listener<String>() {
//        @Override
//        public void onResponse(String response) {
//
//            try {
//                JSONObject jsonResponse = new JSONObject(response);
//                boolean success = jsonResponse.getBoolean("success");
//                if(success){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                    builder.setMessage("삭제성공")
//                            .setPositiveButton("확인",null).create().show();
//                    removeUser(id);
//                    mAdapter.swapCursor(getAllSocial());
//                }else{
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                    builder.setMessage("삭제실패")
//                            .setNegativeButton("취소",null).create().show();
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//    };
//    // 리스트를 갱신한다.
//    DeleteRequest deleteRequest = new DeleteRequest(stName,responseListener);
//    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
//                queue.add(deleteRequest);
    //-----------------------------------------------------------------------------------------------회원 삭제



    private long addNew(String name, String title) {
        // DB에 데이터를 추가를 하기 위해선 ContentValue 객체를 사용해야 한다.
        ContentValues cv = new ContentValues();
        /*
         * 열의 이름을 키로 해서 해당 값을 가리킨다.
         * 값들을 put 메서드를 사용해 입력한다.
         * 첫번째 파라미터는 열의 이름으로, Contract 로부터 가져올 수 있다.
         * 두번째 파라미터는 값이다.
         */
        cv.put(MyContract.MyEntry.COLUMN_USER_NAME, name);
        cv.put(MyContract.MyEntry.COLUMN_TEXT, title);
        // cv에 저장된 값을 사용하여 새로운 행을 추가한다.
        return mDb.insert(MyContract.MyEntry.TABLE_NAME, null, cv);
    }

    private boolean removeUser(long id) {
        // 성공적인 삭제이면 true 를 리턴하고, 실패하면 false 를 리턴한다.
        return mDb.delete(MyContract.MyEntry.TABLE_NAME, MyContract.MyEntry._ID + "=" + id, null) > 0;
    }

    //-----------------------------------------------------------------------------------------------
    private Cursor getAllSocial() {
        // 두번째 파라미터 (Projection array)는 여러 열들 중에서 출력하고 싶은 것만 선택해서 출력할 수 있게 한다.
        // 모든 열을 출력하고 싶을 때는 null 을 입력한다.
        Intent intent = getIntent();
        String a = intent.getStringExtra("userID");
        String[] whereParams = {a};
        return mDb.query(
                MyContract.MyEntry.TABLE_NAME,
                null,
                MyContract.MyEntry.COLUMN_USER_NAME + "= ?",
                whereParams,
                null,
                null,
                null
        );
    }
    //-----------------------------------------------------------------------------------------------Intent 받아온 결과
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            Toast.makeText(MainActivity.this, "결과가 성공이 아님.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (requestCode == REQUEST_ACT) {
            stName = data.getStringExtra("name");
            stTitle = data.getStringExtra("title");
            addNew(stName, stTitle);
            mAdapter.swapCursor(getAllSocial());
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if(success){
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage("서버 올리기 성공.")
                                    .setPositiveButton("확인",null)
                                    .create()
                                    .show();
                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage("서버 올리기 실패.")
                                    .setPositiveButton("취소",null)
                                    .create()
                                    .show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };
            ServerRequest serverRequest = new ServerRequest(stName,stTitle,responseListener);
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            queue.add(serverRequest);

        } else {
            Toast.makeText(MainActivity.this, "REQUEST_ACT가 아님", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(dialog != null){
           dialog.dismiss();
           dialog= null;
        }
    }
    //-----------------------------------------------------------------------------------------------manage관리
    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;
        @Override
        protected void onPreExecute(){
            target= "http://cgd0001.cafe24.com/SocialUserList.php";

        }
        @Override
        protected String doInBackground(Void... voids){
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null){
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String result){
            Intent intent = new Intent(MainActivity.this, ManageActivity.class);
            intent.putExtra("userList",result);
            MainActivity.this.startActivity(intent);
        }
    }
}
