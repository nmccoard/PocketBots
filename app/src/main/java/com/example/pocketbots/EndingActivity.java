package com.example.pocketbots;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
/******************************************
 *   ENDING ACTIVITY CLASS
 *   Animations for the ending game credits.
 ******************************************/
public class EndingActivity extends AppCompatActivity {

    public Button mainMenu;

    public SharedPreferences gameSettings;
    public SharedPreferences.Editor editGame;

    public int level;
    public int height;
    public int width;

    public TextView congratView;

    public ImageView logoView;
    public ImageView bgImageView;
    public ImageView boyImageView;
    public ImageView robotImageView;

    public AnimationDrawable boyAnimation;
    public AnimationDrawable robotAnimation;

    private SoundPool soundPool;
    private int sound07;

    /******************************************
     *   ON CREATE
     ******************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);

        // Get screen size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        // Set Views
        congratView = (TextView) findViewById(R.id.congratView);

        logoView = (ImageView) findViewById(R.id.logoView);
        bgImageView = (ImageView) findViewById(R.id.bgImageView);
        boyImageView = (ImageView) findViewById(R.id.boyImageView);
        robotImageView = (ImageView) findViewById(R.id.robotImageView);

        //button set to invisible until credit sequence is finished
        mainMenu = (Button) findViewById(R.id.menuBTN);
        mainMenu.setVisibility(View.INVISIBLE);

        // Get Game Level
        gameSettings = getSharedPreferences("GameSettings", Context.MODE_PRIVATE);
        editGame = gameSettings.edit();
        level = gameSettings.getInt("level", 0);

        // SoundPool Implementation (This is for SFX/Hitsounds, etc.)
        // Due to the old method and the new method we need to determine
        // if it is possible to use either or so an error would not produce
        // as a result of its implementation.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(1)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }

        // Here we load the mp3 files after the tools are set.
        sound07 = soundPool.load(this, R.raw.sound07, 1);
    }
    /******************************************
     *   EXIT GAME
     *   Exit to main menu
     ******************************************/
    public void exitGame(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        // Victory Song SFX Implementation
        switch (view.getId()) {
            case R.id.menuBTN:
                soundPool.play(sound07, 1, 1, 0, 0, 1);
                break;
        }
    }
    /******************************************
     *   ON START
     ******************************************/
    @Override
    protected void onStart() {
        super.onStart();

        // Set Credits Animation
        congratView.setY((float) (height));
        congratView.setX((float) (0));
        congratView.animate().translationYBy((float)(-height * 2)).setDuration(20000);

        // Set Logo Animation
        logoView.setY((float) (height * 2));
        logoView.setX((float) (0));
        logoView.animate().translationYBy((float)(-height * 2)).setDuration(25000);

        // Set Boy Animation
        boyImageView.setBackgroundResource(R.drawable.boyrun);
        boyAnimation = (AnimationDrawable) boyImageView.getBackground();
        boyImageView.setY((float) (height * .01));
        boyImageView.setX((float) (-width));

        // 1st Boy Run, from left to right
        boyAnimation.start();
        boyImageView.animate().translationXBy((float) (width * 2.3)).setDuration(10000);

        // Set Robot Animation and 1st Robot Run, from left to right
        robotImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                robotImageView.setScaleX(-1); //reverse image
                robotImageView.setBackgroundResource(R.drawable.robotrun);
                robotAnimation = (AnimationDrawable) robotImageView.getBackground();
                robotImageView.setY((float)(height*.56));
                robotImageView.setX((float) (-width));
                robotAnimation.start();
                robotImageView.animate().translationXBy((float) (width * 2)).setDuration(10000);
            }
        }, 1000);

        // 2nd Boy Run, from right to left
        boyImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                boyImageView.setScaleX(-1); //reverse image
                boyImageView.setBackgroundResource(R.drawable.boyrun);
                boyAnimation = (AnimationDrawable) boyImageView.getBackground();
                boyImageView.setX((float) (width));
                boyAnimation.start();
                boyImageView.animate().translationXBy((float) (-width * 2.5)).setDuration(10000);
            }
        }, 10000);

        // 2nd Robot Run, from right to left
        robotImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                robotImageView.setScaleX(1); //set image to normal
                robotImageView.setBackgroundResource(R.drawable.robotrun);
                robotAnimation = (AnimationDrawable) robotImageView.getBackground();
                robotImageView.setX((float) (width));
                robotAnimation.start();
                robotImageView.animate().translationXBy((float) (-width * 2)).setDuration(10000);
            }
        }, 11000);

        // 3rd Boy Run, from left to idling in mid-screen
        boyImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                boyImageView.setScaleX(1); //set image to normal
                boyImageView.setBackgroundResource(R.drawable.boyrun);
                boyAnimation = (AnimationDrawable) boyImageView.getBackground();
                boyImageView.setX((float) (-width));
                boyAnimation.start();
                boyImageView.animate().translationXBy((float) (width * 1.4)).setDuration(7000);
                    boyImageView.postDelayed(new Runnable() {
                    @Override
                    public void run() {  // boy idle animation
                            boyImageView.setBackgroundResource(R.drawable.boyidle);
                            boyAnimation = (AnimationDrawable) boyImageView.getBackground();
                            boyAnimation.start();
                            boyImageView.postDelayed(new Runnable() {
                            @Override
                            public void run() { //boy fainting animation
                                boyImageView.setScaleX(-1); //reverse image
                                ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) boyImageView.getLayoutParams();
                                params.width *= 1.75;  //change width, since faint png is much wider
                                params.height *= 1.014; //slightly change height, since faint png is a little taller
                                boyImageView.setBackgroundResource(R.drawable.boyfaint);
                                boyAnimation = (AnimationDrawable) boyImageView.getBackground();
                                boyAnimation.start();
                                boyImageView.animate().translationXBy((float) (-width * .1)).setDuration(700);
                                boyImageView.postDelayed(new Runnable() {
                                    @Override
                                    public void run() { //replace image with last faint frame
                                        boyImageView.setScaleX(-1); //reverse image
                                        boyImageView.setBackgroundResource(R.drawable.boyfaint5);
                                        mainMenu.setVisibility(View.VISIBLE); // make mainMenu button visible
                                    }
                                }, 700);
                            }
                        }, 1400);
                    }
                }, 7000);
            }
        }, 15000);

        // 3rd Robot Run, from right to mid-screen
        robotImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                robotImageView.setBackgroundResource(R.drawable.robotrun);
                robotAnimation = (AnimationDrawable) robotImageView.getBackground();
                robotImageView.setX((float) (width));
                robotAnimation.start();
                robotImageView.animate().translationXBy((float) (-width * .5)).setDuration(1500);
                robotImageView.postDelayed(new Runnable() {
                    @Override
                    public void run() { //Robot Headbutt animation
                        robotImageView.setBackgroundResource(R.drawable.robotmelee);
                        robotAnimation = (AnimationDrawable) robotImageView.getBackground();
                        robotAnimation.start();
                        robotImageView.postDelayed(new Runnable() {
                            @Override
                            public void run() { //Robot Idle animation
                                robotImageView.setBackgroundResource(R.drawable.robotidle);
                                robotAnimation = (AnimationDrawable) robotImageView.getBackground();
                                robotAnimation.start();
                            }
                        }, 650);
                    }
                }, 1000);
            }
        }, 22000);
    }
    /******************************************
     *   ON STOP
     *   Stop animations
     ******************************************/
    @Override
    protected void onStop() {
        super.onStop();
        boyAnimation.stop();
        robotAnimation.stop();
    }
}
