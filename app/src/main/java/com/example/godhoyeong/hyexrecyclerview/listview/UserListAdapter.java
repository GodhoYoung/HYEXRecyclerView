package com.example.godhoyeong.hyexrecyclerview.listview;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.godhoyeong.hyexrecyclerview.R;
import com.example.godhoyeong.hyexrecyclerview.management.DeleteRequest;
import com.example.godhoyeong.hyexrecyclerview.management.ManageActivity;
import com.example.godhoyeong.hyexrecyclerview.management.ServerDeleteRequest2;
import com.example.godhoyeong.hyexrecyclerview.server.ServerDeleteRequest;

import org.json.JSONObject;

import java.util.List;

public class UserListAdapter extends BaseAdapter {
    private Context context;
    private List<User> userList;
    private List<User> saveList;
    private Activity parentActivity;

    public UserListAdapter(Context context, List<User> userList, List<User> saveList, Activity parentActivity) {
        this.context = context;
        this.userList = userList;
        this.saveList = saveList;
        this.parentActivity = parentActivity;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.user_manage,null);
        final TextView userID = (TextView)v.findViewById(R.id.userID);
        TextView userPassword = (TextView)v.findViewById(R.id.userPassword);
        TextView userEmail = (TextView)v.findViewById(R.id.userEmail);
        final TextView userName = (TextView)v.findViewById(R.id.userName);

        userID.setText(userList.get(i).getUserID());
        userPassword.setText(userList.get(i).getUserPassword());
        userEmail.setText(userList.get(i).getUserEmail());
        userName.setText(userList.get(i).getUserName());

        v.setTag(userList.get(i).getUserID());

        Button btnDelete = (Button)v.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            try {
                JSONObject jsonResponse = new JSONObject(response);
                boolean success = jsonResponse.getBoolean("success");
                if(success){
                    AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
                    builder.setMessage(userID.getText().toString()+"회원 삭제성공")
                            .setPositiveButton("확인",null).create().show();
                   userList.remove(i);
                   for(int i = 0; i< saveList.size(); i++) {
                       if(saveList.get(i).getUserID().equals(userID.getText().toString())){
                           saveList.remove(i);
                           break;
                       }
                   }
                   notifyDataSetChanged();


                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
                    builder.setMessage("회원 삭제실패")
                            .setNegativeButton("취소",null).create().show();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };
    // 리스트를 갱신한다.
    DeleteRequest deleteRequest = new DeleteRequest(userID.getText().toString(),responseListener);
    RequestQueue queue = Volley.newRequestQueue(parentActivity);

    ServerDeleteRequest2 serverDeleteRequest = new ServerDeleteRequest2(userID.getText().toString(),responseListener);
                queue.add(deleteRequest);
                queue.add(serverDeleteRequest);
            }
        });

        return v;
    }
}
