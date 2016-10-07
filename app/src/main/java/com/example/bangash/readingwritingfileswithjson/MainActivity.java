package com.example.bangash.readingwritingfileswithjson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnRead = (Button) findViewById(R.id.btnRead);
        Button btnWrite = (Button) findViewById(R.id.btnWrite);


        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileInputStream fis=openFileInput("tours");
                    BufferedInputStream bis=new BufferedInputStream(fis);
                    StringBuffer stringBuffer=new StringBuffer();
                    while (bis.available()!=0)
                    {
                        char c= (char) bis.read();
                        stringBuffer.append(c);
                    }
                    bis.close();
                    fis.close();

                    JSONArray jsonArray=new JSONArray(stringBuffer.toString());
                    StringBuffer ActualData=new StringBuffer();
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        String data=jsonArray.getJSONObject(i).getString("Tour");
                        ActualData.append(data+"\n");
                    }
                    TextView tvInfo= (TextView) findViewById(R.id.tvInfo);
                    tvInfo.setText(ActualData);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONArray data = new JSONArray();
                JSONObject tours;
                tours = new JSONObject();
                try {
                    tours.put("Tour", "Muree");
                    tours.put("Price", 3000);
                    data.put(tours);
                    tours = new JSONObject();
                    tours.put("Tour", "Peshawar");
                    tours.put("Price", 3000);
                    data.put(tours);
                    tours = new JSONObject();
                    tours.put("Tour", "Hangu");
                    tours.put("Price", 3000);
                    data.put(tours);

                    String text=data.toString();
                    FileOutputStream fos= openFileOutput("tours",MODE_PRIVATE);
                    fos.write(text.getBytes());
                    fos.close();
                    TextView tvInfo= (TextView) findViewById(R.id.tvInfo);
                    tvInfo.setText(data.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
