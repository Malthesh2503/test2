/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Nice_MW.controller;

import com.example.Nice_MW.Services.NiceMWservice;
import com.fasterxml.jackson.core.JsonParser;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;

import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author VIS
 */
@RestController
@RequestMapping("api/nice/mw")

public class NiceMWController {

    @Autowired
    @Value("${IsDomainLogin}")
    private Boolean IsDomainLogin;

    @Autowired
    @Value("${BaseUrl}")
    private String BaseUrl;

    @Autowired
    @Value("${OsLogin}")
    private String OsLogin;

    @Autowired
    @Value("${Password}")
    private String Password;

    @Autowired
    @Value("${Domain}")
    private String Domain;

    String resData = "";

    String Tokenvalue = "";

    @GetMapping("/login")
    public String GetValue() throws Exception {
        if (IsDomainLogin == true) {

            try {
                System.out.println("IsDomain in if condition :" + IsDomainLogin);
                System.out.println("Base URL :" + BaseUrl);

                resData = Processor.httpRequestLogin(BaseUrl, OsLogin, Password, Domain);
//                System.out.println("ResData111" + resData);
//                System.out.println("jsonObject" + resData instanceof String);
                //                resData=ResponseData;
                JSONObject jsonObj = new JSONObject(resData);
                String loginToken = jsonObj.getJSONObject("Value").getString("LoginToken");
                Tokenvalue = loginToken;
                System.out.println("Tokenvalue IN CONTROLLER :" + Tokenvalue);

                String Result = jsonObj.getJSONObject("Result").getString("ResultMessage");
                System.out.println("RESULTMESSAGE IN CONTROLLER :" + Result);

//                JSONObject jsonObject=new JSONObject(resData);
       
                
                
              
                return resData;
            } catch (IOException ex) {
                Logger.getLogger(NiceMWController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (IsDomainLogin != true) {
            return "IsDomain is false";
        }

//        System.out.println(IsDomain);
//        return IsDomain;
        return "NULL";
    }

    String ResDataout = "";

    @GetMapping("/logout")
    public String Logout() {
        try {
            ResDataout = Processor.httpRequestLogout(BaseUrl);
            System.out.println("ResDataout in Controller :" + ResDataout);
        } catch (Exception ex) {
            Logger.getLogger(NiceMWController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("ResDataout in logout"+ResDataout);
        return ResDataout;

    }
}
