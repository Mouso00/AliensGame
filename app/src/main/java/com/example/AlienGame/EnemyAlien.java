package com.example.AlienGame;

import static com.example.AlienGame.GameView.screenRatioX;
import static com.example.AlienGame.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class EnemyAlien {

    public int speed = 10  ;
    public boolean wasShot = true;
    int x, y, width,height, enemyAlienCounter;
    Bitmap enemyAlien1, enemyAlien2, enemyAlien3, enemyAlien4;

    EnemyAlien(Resources res){

        enemyAlien1 = BitmapFactory.decodeResource(res, R.drawable.alien1);
        enemyAlien2 = BitmapFactory.decodeResource(res, R.drawable.alien2);
        enemyAlien3 = BitmapFactory.decodeResource(res, R.drawable.alien3);
        enemyAlien4 = BitmapFactory.decodeResource(res, R.drawable.alien4);

        width = enemyAlien1.getWidth();
        height = enemyAlien1.getHeight();

        width /= 4;
        height /= 4;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        enemyAlien1 = Bitmap.createScaledBitmap(enemyAlien1,width,height,false);
        enemyAlien2 = Bitmap.createScaledBitmap(enemyAlien1,width,height,false);
        enemyAlien3 = Bitmap.createScaledBitmap(enemyAlien1,width,height,false);
        enemyAlien4 = Bitmap.createScaledBitmap(enemyAlien1,width,height,false);

        y= -height;
    }

    Bitmap getAlien(){

        if( enemyAlienCounter == 1){
            enemyAlienCounter++;
            return enemyAlien1;
        }
        if( enemyAlienCounter == 2){
            enemyAlienCounter++;
            return enemyAlien1;
        }
        if( enemyAlienCounter == 3){
            enemyAlienCounter++;
            return enemyAlien1;
        }
        enemyAlienCounter =1;

        return enemyAlien4;

    }

    Rect getCollisionShape () {
        return  new Rect (x, y, x+width, y+ height);
    }


}
