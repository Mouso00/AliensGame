package com.example.AlienGame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread thread = new Thread(){

            @Override
            public void run() {
                try {

                    sleep(7000);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally {

                    Intent mainIntent = new Intent(SplashScreen.this,InstructionActivity.class);
                    startActivity(mainIntent);

                }
            }
        };
        thread.start();

    }
}