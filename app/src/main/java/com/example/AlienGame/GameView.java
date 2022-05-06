package com.example.AlienGame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable{
    private Thread thread;
    private boolean isPlaying, isGameOver = false;
    private int screenX, screenY, score = 0;
    public static float screenRatioX, screenRatioY;
    private Paint paint;
    private List<Bullet> bullets;
    private EnemyAlien[] aliens;
    private SharedPreferences prefs;
    private Random random;
    private SoundPool soundPool;
    private int sound;
    private MyAlien alien;
    private GameActivity activity;
    private Background background1, background2 , background3 ,background4 ;

    public GameView(GameActivity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build();

        } else
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

        sound = soundPool.load(activity, R.raw.shoot, 1);

        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());
        background3 = new Background(screenX, screenY, getResources());
        background4 = new Background(screenX, screenY, getResources());


        alien = new MyAlien(this, screenY, getResources());

        bullets = new ArrayList<>();


        // To resize the score inside the game and the color of the counter
        paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.LTGRAY);

        aliens = new EnemyAlien[4];

        for (int i = 0;i < 4; i++) {

            EnemyAlien bird = new EnemyAlien(getResources());
            aliens[i] = bird;

        }

        random = new Random();

    }

    @Override
    public void run() {

        while (isPlaying) {

            update ();
            draw ();
            sleep ();

        }

    }

    private void update () {

        // The background multiplied with the ScreenRatioX / so the screen moves this way
        background1.x -= 10 * screenRatioX;

        if(background1.x + background1.background.getWidth() < 0){
            background1.x = screenX;
        }
        if(background2.x + background2.background.getWidth() < 0){
            background2.x = screenX;
        }
        if(background3.x + background3.background.getWidth() < 0){
            background3.x = screenX;
        }
        if(background4.x + background4.background.getWidth() < 0){
            background4.x = screenX;
        }


        if (alien.isGoingUp)
            alien.y -= 30 * screenRatioY;
        else
            alien.y += 30 * screenRatioY;

        if (alien.y < 0)
            alien.y = 0;

        if (alien.y >= screenY - alien.height)
            alien.y = screenY - alien.height;

        List<Bullet> trash = new ArrayList<>();

        for (Bullet bullet : bullets) {

            if (bullet.x > screenX)
                trash.add(bullet);

            bullet.x += 50 * screenRatioX;

            for (EnemyAlien bird : aliens) {

                if (Rect.intersects(bird.getCollisionShape(),
                        bullet.getCollisionShape())) {

                    score++;
                    bird.x = -1000;
                    bullet.x = screenX + 500;
                    bird.wasShot = true;

                }

            }

        }

        for (Bullet bullet : trash)
            bullets.remove(bullet);

        for (EnemyAlien alien : aliens) {

            alien.x -= alien.speed;

            if (alien.x + alien.width < 0) {

                if (!alien.wasShot) {
                    isGameOver = true;
                    return;
                }

                int bound = (int) (30 * screenRatioX);
                alien.speed = random.nextInt(bound);

                if (alien.speed < 10 * screenRatioX)
                    alien.speed = (int) (10 * screenRatioX);

                alien.x = screenX;
                alien.y = random.nextInt(screenY - alien.height);

                alien.wasShot = false;
            }

            if (Rect.intersects(alien.getCollisionShape(), this.alien.getCollisionShape())) {

                isGameOver = true;
                return;
            }

        }

    }

    private void draw () {

        if (getHolder().getSurface().isValid()) //This will ensure that the SurfaceView object have been successfully initiated
        {

            Canvas canvas = getHolder().lockCanvas(); //Calling this function returns the current canvas that is have been displayed on the screen

            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);
            canvas.drawBitmap(background1.background2,background1.x, background1.y, paint);
            canvas.drawBitmap(background3.background3,background3.x, background3.y, paint);
            canvas.drawBitmap(background1.background4,background1.x, background1.y, paint);

            for (EnemyAlien bird : aliens)
                canvas.drawBitmap(bird.getAlien(), bird.x, bird.y, paint);

            canvas.drawText(score + "", screenX / 2f, 164, paint);

            if (isGameOver) {
                isPlaying = false;
                canvas.drawBitmap(alien.getDead(), alien.x, alien.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                saveIfHighScore();
                waitBeforeExiting ();
                return;
            }

            canvas.drawBitmap(alien.getMyAlien(), alien.x, alien.y, paint);

            for (Bullet bullet : bullets)
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);

            getHolder().unlockCanvasAndPost(canvas); //This function accept the canvas that needed to be draw on the screen

        }

    }

    private void waitBeforeExiting() {

        try {
            Thread.sleep(3000);
            activity.startActivity(new Intent(activity, MainActivity.class));
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void saveIfHighScore() {

        if (prefs.getInt("highscore", 0) < score) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore", score);
            editor.apply();
        }

    }

    private void sleep () {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume () {

        isPlaying = true;
        thread = new Thread(this);
        thread.start();

    }

    public void pause () {

        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() < screenX / 2) {
                    alien.isGoingUp = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                alien.isGoingUp = false;
                if (event.getX() > screenX / 2)
                    alien.toShoot++;
                break;
        }

        return true;
    }

    public void newBullet() {

        if (!prefs.getBoolean("isMute", false))
            soundPool.play(sound, 1, 1, 0, 0, 1);

        Bullet bullet = new Bullet(getResources());
        bullet.x = alien.x + alien.width;
        bullet.y = alien.y + (alien.height / 2);
        bullets.add(bullet);

    }
}

