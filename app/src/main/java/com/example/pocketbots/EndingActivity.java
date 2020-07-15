package com.example.pocketbots;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
    public ImageView boyImageView2;
    public ImageView boyImageView3;
    public AnimationDrawable boyAnimation;
    public AnimationDrawable boyAnimation2;
    public AnimationDrawable boyAnimation3;

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
        boyImageView = (ImageView) findViewById(R.id.boyImageView);
        boyImageView2 = (ImageView) findViewById(R.id.boyImageView2);
        boyImageView3 = (ImageView) findViewById(R.id.boyImageView3);
        gameSettings = getSharedPreferences("GameSettings", Context.MODE_PRIVATE);
        editGame = gameSettings.edit();
        level = gameSettings.getInt("level", 0);
        if (level == 8) {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) boyImageView.getLayoutParams();
            params.width *= 1.25;
            params.height *= 1.25;
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
        boyImageView.animate().translationXBy((float) (width * 2)).setDuration(10000);

        boyImageView2.postDelayed(new Runnable() {
            @Override
            public void run() {
                boyImageView2.setBackgroundResource(R.drawable.boyrun);
                boyAnimation2 = (AnimationDrawable) boyImageView2.getBackground();
                boyImageView2.setY((float) (height * .4));
                boyImageView2.setX((float) (width * 1));
                boyAnimation2.start();
                boyImageView2.animate().translationXBy((float) (-width * 2)).setDuration(10000);
            }
        }, 10000);

        boyImageView3.postDelayed(new Runnable() {
            @Override
            public void run() {
                boyImageView3.setBackgroundResource(R.drawable.boyrun);
                boyAnimation3 = (AnimationDrawable) boyImageView3.getBackground();
                boyImageView3.setY((float) (height * .4));
                boyImageView3.setX((float) (-width * 1));
                boyAnimation3.start();
                boyImageView3.animate().translationXBy((float) (width * 1.4)).setDuration(10000);
                boyImageView3.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        boyImageView3.setBackgroundResource(R.drawable.boydizzy);
                        boyAnimation3 = (AnimationDrawable) boyImageView3.getBackground();
                        boyAnimation3.start();
                        boyImageView3.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                boyImageView3.setBackgroundResource(R.drawable.boyfaint);
                                boyAnimation3 = (AnimationDrawable) boyImageView3.getBackground();
                                boyAnimation3.start();
                                boyImageView3.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        boyImageView3.setBackgroundResource(R.drawable.boyfaint5);
                                    }
                                }, 700);
                            }
                        }, 5000);
                    }
                }, 10000);

            }
        }, 15000);

    }

}
