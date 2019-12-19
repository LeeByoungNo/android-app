package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myapplication.MESSAGE" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void sendMessage(View view){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void goUITest(View view){
        Intent intent = new Intent(this,UITestActivity.class);
        startActivity(intent);
    }

    public void goWebView(View view) {
        Log.d("goWebView","LOG TEST......");


        Intent intent = new Intent(this,WebViewActivity.class);
        startActivity(intent);
    }

    public void setStatus(final String status){


        /*Handler handler = new Handler(){
            public void handleMessage(Message msg){
                EditText editText = (EditText) findViewById(R.id.editText2);
                editText.setText("status: "+status);
            }
        };
        handler.sendMessage(null);*/

        EditText editText = (EditText) findViewById(R.id.editText2);
        editText.setText("status: "+status);
    }

    public void apiCallTest(View view){
        Log.d("API","API CALL TEST clicked");

        new Thread(){
            public void run(){
//        HttpURLConnection conn = new HttpURLConnection();
                String result = Util.callPostAPI("http://192.168.0.3:8090/testApi");

                Log.d("API","RESULT: "+result);

                try{
                    final JSONObject jsonObject = new JSONObject(result);

                    Log.d("json","status : "+jsonObject.get("status")+", data : "+jsonObject.get("data"));

                    setStatus((String) jsonObject.get("status"));
//                    String status = (String)jsonObject.get("status");

//                    Handler handler = new Handler(){
//                        public void handleMessage(Message msg){
//                            EditText editText = (EditText) findViewById(R.id.editText2);
//                            editText.setText("status: "+status);
//                        }
//                    };

                }catch(Exception e){
                    Log.d("error",""+e);
                }



            }
        }.start();



    }
}
