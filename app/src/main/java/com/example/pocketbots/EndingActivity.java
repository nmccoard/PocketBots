package com.example.pocketbots;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class EndingActivity extends AppCompatActivity {
    public Button mainMenu;
    public SharedPreferences gameSettings;
    public SharedPreferences.Editor editGame;
    public int level;

    public int height;
    public int width;

    public ImageView bgImageView;
    public ImageView boyImageView;
    public AnimationDrawable boyAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);

        // Get screen size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        bgImageView = (ImageView) findViewById(R.id.bgImageView);
        mainMenu = (Button) findViewById(R.id.backMenubutton);
        boyImageView = (ImageView) findViewById(R.id.boyImageView);
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

        boyImageView.setBackgroundResource(R.drawable.boyrun);
        boyAnimation = (AnimationDrawable) boyImageView.getBackground();
        boyImageView.setX(-350);
        boyImageView.setY((float) (height * .01));
        boyImageView.setX((float) (-width * .25));
        boyAnimation.start();
        boyImageView.animate().translationXBy((float) (width * .4)).setDuration(3000);
        boyImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                boyImageView.setBackgroundResource(R.drawable.boyidle);
                boyAnimation = (AnimationDrawable) boyImageView.getBackground();
                boyAnimation.start();
            }
        }, 3000);
    }
}
