package com.example.godhoyeong.hyexrecyclerview.register;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class IDValidate extends StringRequest{
    final static private String URL = "http://cgd0001.cafe24.com/SocialIDValidate.php";
    private Map<String, String> parameters;

    public IDValidate(String userID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
