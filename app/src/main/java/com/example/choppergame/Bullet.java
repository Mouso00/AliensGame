package com.example.choppergame;

import static com.example.choppergame.GameView.screenRatioX;
import static com.example.choppergame.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Bullet {

    int x, y, width, height;
    Bitmap bullet;

    Bullet(Resources res){

        bullet = BitmapFactory.decodeResource(res, R.drawable.bullet);

        width = bullet.getWidth();
        height = bullet.getHeight();

        width /=2;
        height /=2;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;
        bullet = Bitmap.createScaledBitmap(bullet,width,height,false);
    }


    Rect getCollisionShape () {
        return  new Rect (x, y, x+width, y+ height);
    }

}
