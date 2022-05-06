package com.example.AlienGame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Background {
    int x =0, y = 0;
    Bitmap background, background2, background3 , background4 ;


    Background(int screenX, int screenY, Resources res)
    {

        background = BitmapFactory.decodeResource(res, R.drawable.background);
        background = Bitmap.createScaledBitmap(background,screenX,screenY,false);

        background2 = BitmapFactory.decodeResource(res, R.drawable.background2);
        background2 = Bitmap.createScaledBitmap(background2,screenX,screenY,false);

        background3 = BitmapFactory.decodeResource(res, R.drawable.background3);
        background3 = Bitmap.createScaledBitmap(background3,screenX,screenY,false);

        background4 = BitmapFactory.decodeResource(res, R.drawable.background4);
        background4 = Bitmap.createScaledBitmap(background4,screenX,screenY,false);


    }

}
