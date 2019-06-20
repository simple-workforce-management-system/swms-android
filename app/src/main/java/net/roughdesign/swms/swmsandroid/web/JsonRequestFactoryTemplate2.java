package net.roughdesign.swms.swmsandroid.web;

import android.support.annotation.Nullable;
import android.util.Log;
import com.android.volley.*;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

public class JsonRequestFactoryTemplate2 {
    private String url;

    public JsonRequest<String> Do(Object object) {


        int method = Request.Method.POST;
        @Nullable String requestBody = new Gson().toJson(object);

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.w("JsonRequestFactory", "onResponse");
            }
        };
        @Nullable Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w("JsonRequestFactory", "onErrorResponse");
            }
        };


        return new JsonRequest<String>(method, url, requestBody, listener, errorListener) {

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString =
                            new String(
                                    response.data,
                                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));

                    return Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));

                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }


        };
    }




}
