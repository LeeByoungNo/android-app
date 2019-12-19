package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DisplayMessageActivity extends AppCompatActivity {

    List<String> readLineList = new ArrayList<String>();
    String allStr = "" ;
    int current = 0 ;

    private static final int READ_REQUEST_CODE = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        final TextView textView = findViewById(R.id.textView);
        textView.setText(message);

        textView.setMovementMethod(new ScrollingMovementMethod());

        // external storage state check
        String state = Environment.getExternalStorageState();

        Intent intentOpenDocu = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intentOpenDocu.addCategory(Intent.CATEGORY_OPENABLE);
        intentOpenDocu.setType("text/plain");
        startActivityForResult(intentOpenDocu, READ_REQUEST_CODE);


        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("state:").setMessage(state);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();*/
        // external storage state check



        // File read TEST ====================
        /*File file = new File("/storage/etc/TestImage.java");

        BufferedReader fr = null ;
        String data = null ;
        StringBuffer sb = new StringBuffer();

        try {
            fr = new BufferedReader(new FileReader(file));

            while((data = fr.readLine()) != null){
                System.out.println("data :"+data);
                sb.append(data+"\n");
                readLineList.add(data);
            }

        }catch (Exception e){
            e.printStackTrace();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("에러:").setMessage("error: "+e);

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }finally {
            if(fr != null){
                try{
                    fr.close();
                }catch (Exception e){

                }

            }
        }
        textView.setText(sb.toString());*/

        // File read TEST ====================


        Button increaseBtn = findViewById(R.id.button2);
        increaseBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                current = Math.min(current + 1, readLineList.size()-1);

                textView.setText(readLineList.get(current));
            }
        }) ;

        Button decreaseBtn = findViewById(R.id.button3);
        decreaseBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                //
                current = Math.max(current - 1, 0);

                textView.setText(readLineList.get(current));
            }
        });

        Button allBtn = findViewById(R.id.button4);
        allBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                textView.setText(allStr);
            }
        });

        Button btnMag = findViewById(R.id.btnMag);
        btnMag.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                // font  크기 확대
                float cs = textView.getTextSize() ;
            }
        });

        Button btnMin = findViewById(R.id.btnMin);
        btnMin.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                // font  크기 축소
                float cs = textView.getTextSize() ;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent resultData) {

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();

                /*AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("uri:").setMessage(uri+"");

                AlertDialog alertDialog = builder.create();
                alertDialog.show();*/

                StringBuilder stringBuilder = new StringBuilder();

                try (InputStream inputStream =
                             getContentResolver().openInputStream(uri);
                     BufferedReader reader = new BufferedReader(
                             new InputStreamReader(Objects.requireNonNull(inputStream)))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                        if(line.trim().length() != 0){
                            readLineList.add(line);
                        }

                    }
                }catch (Exception e){

                }
                TextView textView = findViewById(R.id.textView);
                textView.setText(stringBuilder.toString());
                allStr = stringBuilder.toString();
//                getContentResolver().openInputStream(uri);
            }
        }
    }
}
