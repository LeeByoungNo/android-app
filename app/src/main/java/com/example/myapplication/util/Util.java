package com.example.myapplication.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Util {

    public static String callPostAPI(String urlStr){

        StringBuffer sb = new StringBuffer();

        try{

            Log.d("URL",urlStr);
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();

            con.setRequestMethod("POST");

//		con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            con.setDoInput(true);
            con.setDoOutput(true); //POST 데이터를 OutputStream으로 넘겨 주겠다는 설정

            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write("param1=testParam1");
            wr.flush();


            if(con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String line ;
                while((line = br.readLine()) != null) {
                    sb.append(line).append('\n');

                }
                br.close();

                System.out.println("result: "+sb.toString());

            }else {
                System.out.println(con.getResponseMessage());
            }
        }catch (Exception e){
            Log.d("Util","API send error :"+e);
        }


        return sb.toString() ;
    }
}
