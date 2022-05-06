package com.example.AlienGame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class InstructionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        Thread thread = new Thread(){

            @Override
            public void run() {
                try {

                    sleep(9000);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally {

                    Intent mainIntent = new Intent(InstructionActivity.this,MainActivity.class);
                    startActivity(mainIntent);

                }
            }
        };
        thread.start();


    }
}