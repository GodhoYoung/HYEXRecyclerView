package com.example.godhoyeong.hyexrecyclerview.register;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    final static private String URL = "http://cgd0001.cafe24.com/SocialRegister.php";
    private Map<String, String> parameters;

    public  RegisterRequest(String userID, String userPassword, String userEmail,String userName, Response.Listener<String> listener){
        super(Method.POST, URL,listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userEmail", userEmail);
        parameters.put("userName", userName);

    }

    public Map<String, String> getParams(){
        return parameters;
    }
}
