package com.example.mypegasus.testthread;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*try {
                    Thread.sleep(1000);
                    System.out.println(">>>>>>>>>>>>Tick");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            while (true) {
                                Thread.sleep(1000);
                                System.out.println(">>>>>>>>>>>>>>Tick");
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }
}
