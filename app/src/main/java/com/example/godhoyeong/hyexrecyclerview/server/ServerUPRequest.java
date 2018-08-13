package com.example.godhoyeong.hyexrecyclerview.server;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ServerUPRequest extends StringRequest {
    final static private String URL = "http://cgd0001.cafe24.com/SocialText.php";
    private Map<String, String> parameters2;

    public ServerUPRequest(String userID,String userText,  Response.Listener<String> listener){
        super(Method.POST, URL,listener, null);
        parameters2 = new HashMap<>();
        parameters2.put("userID", userID);
        parameters2.put("userText",userText);

    }

    public Map<String, String> getParams(){
        return parameters2;
    }
}
