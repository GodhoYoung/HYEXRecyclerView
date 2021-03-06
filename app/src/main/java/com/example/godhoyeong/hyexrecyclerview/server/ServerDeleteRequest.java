package com.example.godhoyeong.hyexrecyclerview.server;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ServerDeleteRequest extends StringRequest {
    final static private String URL = "http://cgd0001.cafe24.com/SocialTextDelete.php";
    private Map<String, String> parameters;

    public ServerDeleteRequest(String userID ,String userText, Response.Listener<String> listener){
        super(Method.POST, URL,listener, null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("userText", userText);

    }

    public Map<String, String> getParams(){
        return parameters;
    }
}
