package com.example.godhoyeong.hyexrecyclerview.management;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ServerDeleteRequest2 extends StringRequest {
    final static private String URL = "http://cgd0001.cafe24.com/SocialTextDelete2.php";
    private Map<String, String> parameters;

    public ServerDeleteRequest2(String userID , Response.Listener<String> listener){
        super(Method.POST, URL,listener, null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);

    }

    public Map<String, String> getParams(){
        return parameters;
    }
}
