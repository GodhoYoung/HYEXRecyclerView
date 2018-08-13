package com.example.godhoyeong.hyexrecyclerview.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.godhoyeong.hyexrecyclerview.MainActivity;
import com.example.godhoyeong.hyexrecyclerview.R;
import com.example.godhoyeong.hyexrecyclerview.register.RegisterActivity;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword, etRegisterBtn;
    AlertDialog dialog;
    private String userID;
    private String userPassword;
    private String userEmail;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etEmail = (EditText)findViewById(R.id.et_email_log);
        etPassword = (EditText)findViewById(R.id.et_password_log);


        etRegisterBtn = (EditText)findViewById(R.id.et_register_log);

        etRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        final Button btnLogin = (Button)findViewById(R.id.btnLogin_log);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userIDEmail = etEmail.getText().toString();
                String userPassword = etPassword.getText().toString();
                if(userIDEmail.equals("")||userPassword.equals("")){
                    if(userIDEmail.equals("")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        dialog = builder.setMessage("ID 또는 이메일을 입력해주세요.")
                                .setNegativeButton("취소", null)
                                .create();
                        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialogInterface) {
                                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.YELLOW);
                            }
                        });
                                dialog.show();
                        return;
                    }else if(userPassword.equals("")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        dialog = builder.setMessage("비밀번호를 입력해주세요.")
                                .setNegativeButton("취소", null)
                                .create();
                        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialogInterface) {
                                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.YELLOW);
                            }
                        });
                                dialog.show();
                        return;
                    }
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        userID = etEmail.getText().toString();
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){

                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("로그인 성공.")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                                    @Override
                                    public void onShow(DialogInterface dialogInterface) {
                                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                                    }
                                });
                                        dialog.show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("userID",userID);
                                LoginActivity.this.startActivity(intent);
                                finish();
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("로그인 실패.")
                                        .setNegativeButton("취소", null)
                                        .create();
                                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                                    @Override
                                    public void onShow(DialogInterface dialogInterface) {
                                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.YELLOW);
                                    }
                                });

                                        dialog.show();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(userIDEmail, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
                }
        });
    }

}
