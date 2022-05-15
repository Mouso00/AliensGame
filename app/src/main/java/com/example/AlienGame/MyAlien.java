package com.example.AlienGame;

import static com.example.AlienGame.GameView.screenRatioX;
import static com.example.AlienGame.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class MyAlien {

    int toShoot = 0 ;
    boolean isGoingUp;

    {
        isGoingUp = false;
    }

    int x, y, width , height, wingCounter = 0, shootCounter = 1;
    Bitmap myAlien1, myAlien2, shoot1,shoot2,shoot3,shoot4,shoot5, dead ;
    private final GameView gameView;

    MyAlien(GameView gameView , int screenY, Resources res){

        this.gameView = gameView;

        myAlien1 = BitmapFactory.decodeResource(res, R.drawable.fly1);
        myAlien2 = BitmapFactory.decodeResource(res, R.drawable.fly2);

        width = myAlien1.getWidth();
        height = myAlien1.getHeight();

        width /= 4;
        height /= 4;

        width *= (int) screenRatioX;
        height *=(int) screenRatioY;

        myAlien1 = Bitmap.createScaledBitmap(myAlien1,width,height,false);
        myAlien2 = Bitmap.createScaledBitmap(myAlien2,width,height,false);

        shoot1 = BitmapFactory.decodeResource(res, R.drawable.shoot1);
        shoot2 = BitmapFactory.decodeResource(res, R.drawable.shoot2);
        shoot3 = BitmapFactory.decodeResource(res, R.drawable.shoot3);
        shoot4 = BitmapFactory.decodeResource(res, R.drawable.shoot4);
        shoot5 = BitmapFactory.decodeResource(res, R.drawable.shoot5);

        shoot1 = Bitmap.createScaledBitmap(shoot1,width,height,false);
        shoot2 = Bitmap.createScaledBitmap(shoot2,width,height,false);
        shoot3 = Bitmap.createScaledBitmap(shoot3,width,height,false);
        shoot4 = Bitmap.createScaledBitmap(shoot4,width,height,false);
        shoot5 = Bitmap.createScaledBitmap(shoot5,width,height,false);

        dead = BitmapFactory.decodeResource(res, R.drawable.dead);
        dead = Bitmap.createScaledBitmap(dead,width,height, false);
         y = screenY / 2;
         x = (int) (64* screenRatioX);

    }
    Bitmap getMyAlien(){

        if(toShoot != 0){

            if (shootCounter == 1){
                shootCounter++;
                return shoot1;
            }
            if (shootCounter == 2){
                shootCounter++;
                return shoot2;
            }
            if (shootCounter == 3){
                shootCounter++;
                return shoot3;
            }
            if (shootCounter == 4){
                shootCounter++;
                return shoot4;
            }
            shootCounter = 1;
            toShoot--;
            gameView.newBullet();

            return shoot5;

        }

        if(wingCounter == 0){
            wingCounter++;
            return myAlien1;
        }
        wingCounter--;

        return myAlien2;
    }

    Rect getCollisionShape () {
        return  new Rect (x, y, x+1, y+ 1);
    }

    Bitmap getDead(){
        return dead;
    }

}
