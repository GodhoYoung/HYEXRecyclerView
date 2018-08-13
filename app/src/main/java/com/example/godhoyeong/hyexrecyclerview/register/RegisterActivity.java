package com.example.godhoyeong.hyexrecyclerview.register;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.godhoyeong.hyexrecyclerview.R;
import com.example.godhoyeong.hyexrecyclerview.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {


    private String userID;
    private String userPassword;
    private String userEmail;
    private String userName;

    private boolean idValidate = false;
    private boolean emailValidate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etID = (EditText)findViewById(R.id.et_id_reg);
        final EditText etEmail = (EditText)findViewById(R.id.et_email_reg);
        final EditText etPassword = (EditText)findViewById(R.id.et_password_reg);
        final EditText etName = (EditText)findViewById(R.id.et_name_reg);

        final Button btnIDValidate = (Button)findViewById(R.id.btn_idValidate);
        btnIDValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = etID.getText().toString();
                if(idValidate){
                    return;
                }
                if(userID.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("아이디를 빈칸 없이 입력해주세요")
                            .setNegativeButton("취소", null)
                            .create().show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext(),"성공",Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("사용가능한 아이디 입니다.")
                                        .setPositiveButton("확인", null)
                                        .create().show();
                                etID.setEnabled(false);
                                idValidate = true;
                                btnIDValidate.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                            }else{
                                Toast.makeText(getApplicationContext(),"실패",Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage(" 사용 불가능한 아이디 입니다.")
                                        .setNegativeButton("취소", null)
                                        .create().show();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };

                IDValidate idValidate = new IDValidate(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(idValidate);
            }
        });


        final Button btnEmailValidate = (Button)findViewById(R.id.btn_emailValidate);
        btnEmailValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = etEmail.getText().toString();
                if(emailValidate){
                    return;
                }
                if(userEmail.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("이메일을 빈칸 없이 입력해주세요")
                            .setNegativeButton("취소", null)
                            .create().show();
                    return;
                }

                Response.Listener<String> responseListener2 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse2 = new JSONObject(response);
                            boolean success = jsonResponse2.getBoolean("success2");
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("사용가능한 이메일 입니다.")
                                        .setPositiveButton("확인", null)
                                        .create().show();
                                etEmail.setEnabled(false);
                                emailValidate = true;
                                btnEmailValidate.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage(" 사용 불가능한 이메일 입니다.")
                                        .setNegativeButton("취소", null)
                                        .create().show();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };

                EmailValidate emailValidate = new EmailValidate( userEmail, responseListener2);
                RequestQueue queue2 = Volley.newRequestQueue(RegisterActivity.this);
                queue2.add(emailValidate);
            }
        });

        Button btnRegister = (Button)findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = etID.getText().toString();
                String userPassword = etPassword.getText().toString();
                String userEmail = etEmail.getText().toString();
                String userName = etName.getText().toString();

                if(!idValidate){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("아이디 중복확인 해주세요")
                            .setNegativeButton("취소",null)
                            .create()
                            .show();
                    return;
                }
                if(!emailValidate){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("이메일 중복확인 해주세요")
                            .setNegativeButton("취소",null)
                            .create()
                            .show();
                    return;
                }


                if(userID.equals("")||userPassword.equals("")||userEmail.equals("")||userName.equals("")){
                    if(userID.equals("")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage("아이디를 입력해주세요.")
                                .setNegativeButton("취소", null)
                                .create()
                                .show();
                        return;
                    }else if(userPassword.equals("")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage("비밀번호를 빈칸 없이 입력 해주세요.")
                                .setNegativeButton("취소", null)
                                .create()
                                .show();
                        return;
                    }else if(userEmail.equals("")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage("이메일을 빈칸 없이 입력 해주세요.")
                                .setNegativeButton("취소", null)
                                .create()
                                .show();
                        return;
                    }else if(userName.equals("")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage("이름을 빈칸 없이 입력 해주세요.")
                                .setNegativeButton("취소", null)
                                .create()
                                .show();
                        etName.hasFocus();
                        return;
                    }
                }




                Response.Listener<String> responseListener3 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse3 = new JSONObject(response);
                            boolean success = jsonResponse3.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                        builder.setMessage("회원가입 성공")
                                        .setPositiveButton("확인",null)
                                        .create()
                                        .show();
                                finish();

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("회원가입 실패")
                                        .setNegativeButton("확인",null)
                                        .create()
                                        .show();
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(userID,userPassword,userEmail,userName, responseListener3);
                RequestQueue queue3 = Volley.newRequestQueue(RegisterActivity.this);
                queue3.add(registerRequest);

            }
        });
    }
}
