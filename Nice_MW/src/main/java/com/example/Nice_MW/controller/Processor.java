/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Nice_MW.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author VIS
 */
public class Processor {

    public static String TokenValue = "";
    public static String responseData = null;

    static TrustManager[] trustAllCerts = new TrustManager[]{
        new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }
        }
    };

    public static String httpRequestLogin(String BaseUrl, String OsLogin, String Password, String Domain) throws Exception {
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

        OkHttpClient.Builder newBuilder = new OkHttpClient.Builder();
        newBuilder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
        newBuilder.hostnameVerifier((hostname, session) -> true);

        OkHttpClient client = newBuilder.build();
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, "{\r\n\"OsLogin\":\"" + OsLogin + "\",\r\n\"Password\":\"" + Password + "\",\r\n\"Domain\": \"" + Domain + "\"\r\n}");
        Request request = new Request.Builder()
                .url("https://" + BaseUrl + "/NICEConnectAPI/Login.svc/Rest/ClientLoginWithDomain")
                .method("POST", body)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .build();
        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            responseData = response.body().string();
            System.out.println("responseData in login :" + responseData);
//           JSONArray jsonArr=new JSONArray(responseData);
            JSONObject jsonObj = new JSONObject(responseData);
            String logToken = jsonObj.getJSONObject("Value").getString("LoginToken");
            String Result = jsonObj.getJSONObject("Result").getString("ResultMessage");
            //    String Data= jsonArray.toString();
            TokenValue = logToken;
            System.out.println("TokenValue" + TokenValue);
            System.out.println("Result" + Result);
        }
        System.out.println("responseData" + responseData);
        return responseData;
    }

    public static String httpRequestLogout(String BaseUrl) throws Exception {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            OkHttpClient.Builder newBuilder = new OkHttpClient.Builder();
            newBuilder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
            newBuilder.hostnameVerifier((hostname, session) -> true);

            OkHttpClient client = newBuilder.build();
            System.out.println("responseData in logout" + responseData);
            JSONObject jsonObj = new JSONObject(responseData);
            String logToken = jsonObj.getJSONObject("Value").getString("LoginToken");
            TokenValue = logToken;
            System.out.println("TokenValue inside logout:" + TokenValue);

            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
//            System.out.println("{\r\n\r\n\"LoginToken\": \"" + TokenValue + "\"\r\n\r\n}");
            RequestBody body = RequestBody.create(mediaType, "{\r\n\r\n\"LoginToken\": \"" + TokenValue + "\"\r\n\r\n}");
            
            Request request = new Request.Builder()
                    .url("https://" + BaseUrl + "/NICEConnectAPI/Login.svc/Rest/ServerLogOut")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .build();
            Response response = client.newCall(request).execute();
           JSONObject jsonObj1 = new JSONObject(responseData);
            String ResultInLogOut = jsonObj.getJSONObject("Result").getString("ResultMessage");
            System.out.println("ResultInLogOut" + ResultInLogOut);
            
            return responseData;
        } catch (IOException ex) {
            Logger.getLogger(Processor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
