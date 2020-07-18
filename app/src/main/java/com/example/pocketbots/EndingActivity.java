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
    //public ImageView boyImageView2;
    //public ImageView boyImageView3;
    public AnimationDrawable boyAnimation;
    //public AnimationDrawable boyAnimation2;
    //public AnimationDrawable boyAnimation3;

    public ImageView robotImageView;
    public AnimationDrawable robotAnimation;

    private SoundPool soundPool;
    private int sound07;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);

        // Get screen size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        congratView = (TextView) findViewById(R.id.congratView);
        logoView = (ImageView) findViewById(R.id.logoView);

        bgImageView = (ImageView) findViewById(R.id.bgImageView);
        mainMenu = (Button) findViewById(R.id.menuBTN);
        mainMenu.setVisibility(View.INVISIBLE);
        boyImageView = (ImageView) findViewById(R.id.boyImageView);
        //boyImageView2 = (ImageView) findViewById(R.id.boyImageView2);
        //boyImageView3 = (ImageView) findViewById(R.id.boyImageView3);
        robotImageView = (ImageView) findViewById(R.id.robotImageView);
        gameSettings = getSharedPreferences("GameSettings", Context.MODE_PRIVATE);
        editGame = gameSettings.edit();
        level = gameSettings.getInt("level", 0);
        if (level == 8) {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) boyImageView.getLayoutParams();
            params.width *= 1.25;
            params.height *= 1.25;
        }

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

    public void exitGame(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        // SFX Implementation
        switch (view.getId()) {
            case R.id.menuBTN:
                soundPool.play(sound07, 1, 1, 0, 0, 1);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        congratView.setY((float) (height * 1));
        congratView.setX((float) (width * 0));
        congratView.animate().translationYBy((float)(-height * 2)).setDuration(20000);

        logoView.setY((float) (height * 2));
        logoView.setX((float) (width * 0));
        logoView.animate().translationYBy((float)(-height * 2)).setDuration(25000);

        boyImageView.setBackgroundResource(R.drawable.boyrun);
        boyAnimation = (AnimationDrawable) boyImageView.getBackground();
        boyImageView.setY((float) (height * .01));
        boyImageView.setX((float) (-width * 1));
        boyAnimation.start();
        boyImageView.animate().translationXBy((float) (width * 2.3)).setDuration(10000);

        robotImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                robotImageView.setScaleX(-1);
                robotImageView.setBackgroundResource(R.drawable.robotrun);
                robotAnimation = (AnimationDrawable) robotImageView.getBackground();
                //robotImageView.setY((float) (height * .5));
                robotImageView.setY((float)(height*.56));
                robotImageView.setX((float) (-width * 1));
                robotAnimation.start();
                robotImageView.animate().translationXBy((float) (width * 2)).setDuration(10000);
            }
        }, 1000);

        boyImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                boyImageView.setScaleX(-1);
                boyImageView.setBackgroundResource(R.drawable.boyrun);
                boyAnimation = (AnimationDrawable) boyImageView.getBackground();
                //boyImageView.setY((float) (height * .4));
                boyImageView.setX((float) (width * 1));
                boyAnimation.start();
                boyImageView.animate().translationXBy((float) (-width * 2.5)).setDuration(10000);
            }
        }, 10000);

        robotImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                robotImageView.setScaleX(1);
                robotImageView.setBackgroundResource(R.drawable.robotrun);
                robotAnimation = (AnimationDrawable) robotImageView.getBackground();
                //robotImageView.setY((float) (height * .5));
                robotImageView.setX((float) (width * 1));
                robotAnimation.start();
                robotImageView.animate().translationXBy((float) (-width * 2)).setDuration(10000);
            }
        }, 11000);

        boyImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                boyImageView.setScaleX(1);
                boyImageView.setBackgroundResource(R.drawable.boyrun);
                boyAnimation = (AnimationDrawable) boyImageView.getBackground();
                //boyImageView.setY((float) (height * .4));
                boyImageView.setX((float) (-width * 1));
                boyAnimation.start();
                boyImageView.animate().translationXBy((float) (width * 1.4)).setDuration(7000);
                    boyImageView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                            boyImageView.setBackgroundResource(R.drawable.boyidle);
                            boyAnimation = (AnimationDrawable) boyImageView.getBackground();
                            boyAnimation.start();
                            boyImageView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                boyImageView.setScaleX(-1);
                                ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) boyImageView.getLayoutParams();
                                params.width *= 1.75;
                                params.height *= 1.014;
                                boyImageView.setBackgroundResource(R.drawable.boyfaint);
                                boyAnimation = (AnimationDrawable) boyImageView.getBackground();
                                boyAnimation.start();
                                boyImageView.animate().translationXBy((float) (-width * .1)).setDuration(700);
                                boyImageView.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        boyImageView.setScaleX(-1);
                                        boyImageView.setBackgroundResource(R.drawable.boyfaint5);
                                        //boyImageView3.animate().translationXBy((float) (width * 1.45)).setDuration(100);
                                        //boyAnimation3.start();
                                        mainMenu.setVisibility(View.VISIBLE);
                                    }
                                }, 700);
                            }
                        }, 1400);
                    }
                }, 7000);
            }
        }, 15000);

        robotImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                //robotImageView.setScaleX(1);
                robotImageView.setBackgroundResource(R.drawable.robotrun);
                robotAnimation = (AnimationDrawable) robotImageView.getBackground();
                //robotImageView.setY((float) (height * .5));
                robotImageView.setX((float) (width * 1));
                robotAnimation.start();
                robotImageView.animate().translationXBy((float) (-width * .5)).setDuration(1500);
                robotImageView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        robotImageView.setBackgroundResource(R.drawable.robotmelee);
                        robotAnimation = (AnimationDrawable) robotImageView.getBackground();
                        robotAnimation.start();
                        robotImageView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
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
    @Override
    protected void onStop() {
        super.onStop();
        boyAnimation.stop();
        //monsterAnimation.stop();
        robotAnimation.stop();
    }
}
