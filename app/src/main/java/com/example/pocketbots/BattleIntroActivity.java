package com.example.pocketbots;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class BattleIntroActivity extends AppCompatActivity {

    public  Button battleButton;

    public SharedPreferences gameSettings;
    public SharedPreferences.Editor editGame;
    public int level;

    public int height;
    public int width;

    public ImageView bgImageView;
    public ImageView boyImageView;
    public ImageView monsterImageView;
    public ImageView robotImageView;

    public AnimationDrawable boyAnimation;
    public AnimationDrawable monsterAnimation;
    public AnimationDrawable robotAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_intro);

        // Get screen size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        bgImageView = (ImageView) findViewById(R.id.bgImageView);
        battleButton = (Button) findViewById(R.id.battleButton);
        boyImageView = (ImageView) findViewById(R.id.boyImageView);
        monsterImageView = (ImageView) findViewById(R.id.monsterImageView);
        robotImageView = (ImageView) findViewById(R.id.robotImageView);

        gameSettings = getSharedPreferences("GameSettings", Context.MODE_PRIVATE);
        editGame = gameSettings.edit();
        level = gameSettings.getInt("level", 0);
        if (level == 7) {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) monsterImageView.getLayoutParams();
            params.width *= 1.25;
            params.height *= 1.25;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        boyImageView.setBackgroundResource(R.drawable.boyrun);
        boyAnimation = (AnimationDrawable) boyImageView.getBackground();
        boyImageView.setX(-500);

        robotImageView.setBackgroundResource(R.drawable.robotrun);
        robotAnimation = (AnimationDrawable) robotImageView.getBackground();
        robotImageView.setX(-500);

        boyImageView.setY((float)(height*.01));
        boyImageView.setX((float)(-width*.25));

        robotImageView.setY((float)(height*.01));

        monsterImageView.setY((float)(height*.01));
        monsterImageView.setX((float)(width*.05));

        setMonsterAnimation();

        boyAnimation.start();
        robotAnimation.start();
        monsterAnimation.start();
        boyImageView.animate().translationXBy((float)(width*.35)).setDuration(3000);
        robotImageView.animate().translationXBy((float)(width*.35)).setDuration(3000);

        boyImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                boyImageView.setBackgroundResource(R.drawable.boyidle);
                boyAnimation = (AnimationDrawable) boyImageView.getBackground();
                boyAnimation.start();
            }
        }, 3000);

        robotImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                robotImageView.setBackgroundResource(R.drawable.robotidle);
                robotAnimation = (AnimationDrawable) robotImageView.getBackground();
                robotAnimation.start();
            }
        }, 3000);
    }

    public void exitGame(View view) {
        boyAnimation.stop();
        monsterAnimation.stop();
        robotAnimation.stop();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void battle(View view) {
        boyAnimation.stop();
        monsterAnimation.stop();
        robotAnimation.stop();
        Intent intent = new Intent(this, BattleScreenActivity.class);
        startActivity(intent);
    }

    public void setMonsterAnimation() {

        switch(level) {
            case 1:
                monsterImageView.setBackgroundResource(R.drawable.redmonsteridle);
                break;
            case 2:
                monsterImageView.setBackgroundResource(R.drawable.skullidle);
                break;
            case 3:
                monsterImageView.setBackgroundResource(R.drawable.blueidle);
                break;
            case 4:
                monsterImageView.setBackgroundResource(R.drawable.greyidle);
                break;
            case 5:
                monsterImageView.setBackgroundResource(R.drawable.gridle);
                break;
            case 6:
                monsterImageView.setBackgroundResource(R.drawable.pinkidle);
                break;
            case 7:
                monsterImageView.setBackgroundResource(R.drawable.orangeidle);
                break;
        }

        monsterAnimation = (AnimationDrawable) monsterImageView.getBackground();
    }

    @Override
    protected void onStop() {

        super.onStop();
        boyAnimation.stop();
        monsterAnimation.stop();
        robotAnimation.stop();

    }
}