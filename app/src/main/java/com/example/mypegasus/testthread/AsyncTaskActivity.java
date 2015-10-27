package com.example.mypegasus.testthread;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class AsyncTaskActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

        textView = (TextView) findViewById(R.id.textView);
        findViewById(R.id.btn_read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readURL("http://www.baidu.com");
            }
        });
    }

    private void readURL(String url) {
        new AsyncTask<String, Float, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    System.out.println(params.length);
                    System.out.println(params[0]);
                    URL url = new URL(params[0]);
                    URLConnection urlConnection = url.openConnection();
                    long total = urlConnection.getContentLength();
                    System.out.println("total:" + total); //total:-1
                    InputStream is = urlConnection.getInputStream();
//                    InputStream is = url.openStream();// = url.openConnection().getInputStream();

                    InputStreamReader isr = new InputStreamReader(is, "utf-8");
                    BufferedReader br = new BufferedReader(isr);
                    String line;
                    StringBuilder builder = new StringBuilder();
                    List<Integer> lengthList = new ArrayList();
                    while ((line = br.readLine()) != null) {
                        builder.append(line);
//                        publishProgress((float)builder.length() / total);
                        lengthList.add(builder.length());
                    }
                    total = builder.length();
                    System.out.println("length:" + total);
                    for (int i = 0; i < lengthList.size(); i++) {
                        int length = lengthList.get(i);
                        System.out.println("length " + i + ":" + length);
                        publishProgress((float)length / total);
                    }
                    br.close();
                    isr.close();
                    is.close();

                    return builder.toString();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                Toast.makeText(AsyncTaskActivity.this, "开始读取...", Toast.LENGTH_SHORT).show();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                textView.setText(s);
                super.onPostExecute(s);
            }

            @Override
            protected void onProgressUpdate(Float... values) {
                System.err.println("progressUpdate:" + values[0]);
                super.onProgressUpdate(values);
            }

            @Override
            protected void onCancelled(String s) {
                super.onCancelled(s);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
            }
        }.execute(url);
    }
}
