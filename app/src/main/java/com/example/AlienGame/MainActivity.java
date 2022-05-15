package com.example.AlienGame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private boolean isMute;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
        animation.setDuration(600); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE);
        View btn = findViewById(R.id.play);
        btn.startAnimation(animation);
        btn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, GameActivity.class)));

        TextView highScoreTxt = findViewById(R.id.highScoreTxt);

        SharedPreferences prefs = getSharedPreferences("game" , MODE_PRIVATE);
        highScoreTxt.setText("HighScore: " + prefs.getInt("high score", 0));

        isMute = prefs.getBoolean("isMute", false);

        ImageView volumeCtrl = findViewById(R.id.volumeCtrl);

        if(isMute)
            volumeCtrl.setImageResource(R.drawable.ic_baseline_volume_off_24);
        else
            volumeCtrl.setImageResource(R.drawable.ic_baseline_volume_up_24);

        volumeCtrl.setOnClickListener(v -> {
            isMute = !isMute;
            if(isMute)
                volumeCtrl.setImageResource(R.drawable.ic_baseline_volume_off_24);
            else
                volumeCtrl.setImageResource(R.drawable.ic_baseline_volume_up_24);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean( "isMute" , isMute);
            editor.apply();
        });

    }
}