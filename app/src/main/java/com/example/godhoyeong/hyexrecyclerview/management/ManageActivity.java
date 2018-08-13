package com.example.godhoyeong.hyexrecyclerview.management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.godhoyeong.hyexrecyclerview.R;
import com.example.godhoyeong.hyexrecyclerview.listview.User;
import com.example.godhoyeong.hyexrecyclerview.listview.UserListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ManageActivity extends AppCompatActivity {

    private ListView listView;
    private UserListAdapter adapter;
    private List<User> userList;
    private List<User> saveList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        listView = (ListView)findViewById(R.id.rv_ListManage) ;
        Intent intent = getIntent();
        userList = new ArrayList<User>();
        saveList = new ArrayList<User>();

        adapter = new UserListAdapter(getApplicationContext(), userList, saveList, this);

        listView.setAdapter(adapter);


        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("userList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String userID, userPassword, userEmail, userName;
            while(count< jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                userID = object.getString("userID");
                userPassword = object.getString("userPassword");
                userEmail = object.getString("userEmail");
                userName = object.getString("userName");

                User user = new User(userID, userPassword, userEmail, userName);
                if(!userID.equals("admin")) {
                    userList.add(user);
                    saveList.add(user);
                }
                count++;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();

        EditText etSearch = (EditText)findViewById(R.id.et_Search);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUser(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public void searchUser(String search){
        userList.clear();
        for(int i = 0 ; i< saveList.size(); i++){
            if(saveList.get(i).getUserID().contains(search)){
                userList.add(saveList.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }
}
