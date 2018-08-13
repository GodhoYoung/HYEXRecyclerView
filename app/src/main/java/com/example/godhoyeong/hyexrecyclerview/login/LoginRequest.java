package com.example.godhoyeong.hyexrecyclerview.login;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    final static private String URL = "http://cgd0001.cafe24.com/SocialLogin.php";
    private Map<String, String> parameters;

    public LoginRequest(String userIDEmail, String userPassword,  Response.Listener<String> listener){
        super(Method.POST, URL,listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userIDEmail);
        parameters.put("userEmail", userIDEmail);
        parameters.put("userPassword", userPassword);

    }

    public Map<String, String> getParams(){
        return parameters;
    }
}
