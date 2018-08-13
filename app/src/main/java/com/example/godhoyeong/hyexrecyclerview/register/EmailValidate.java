package com.example.godhoyeong.hyexrecyclerview.register;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class EmailValidate extends StringRequest{
        final static private String URL = "http://cgd0001.cafe24.com/SocialEmailValidate.php";
        private Map<String, String> parameters;

        public EmailValidate( String userEmail, Response.Listener<String> listener) {
            super(Method.POST, URL, listener, null);
            parameters = new HashMap<>();
            parameters.put("userEmail", userEmail);
        }

        @Override
        public Map<String, String> getParams(){
            return parameters;
        }
    }
