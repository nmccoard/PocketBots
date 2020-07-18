package com.example.pocketbots;

import androidx.appcompat.app.AppCompatActivity;

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
/******************************************
 *   BATTLE INTRO ACTIVITY
 *   Animations that take place just before the battle screen.
 ******************************************/
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

    private SoundPool soundPool;
    private int sound06;


    /******************************************
     *   ON CREATE
     ******************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_intro);

        // Get screen size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        // Set Image Views
        bgImageView = (ImageView) findViewById(R.id.bgImageView);
        battleButton = (Button) findViewById(R.id.battleButton);
        boyImageView = (ImageView) findViewById(R.id.boyImageView);
        monsterImageView = (ImageView) findViewById(R.id.monsterImageView);
        robotImageView = (ImageView) findViewById(R.id.robotImageView);

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
        sound06 = soundPool.load(this, R.raw.sound06, 1);
    }

    /******************************************
     *   ON START
     ******************************************/
    @Override
    protected void onStart() {
        super.onStart();

        // Set Boy Animation
        boyImageView.setBackgroundResource(R.drawable.boyrun);
        boyAnimation = (AnimationDrawable) boyImageView.getBackground();
        boyImageView.setY((float)(height*.01));
        boyImageView.setX((float)(-width*.3));

        // Set Robot Animation
        robotImageView.setBackgroundResource(R.drawable.robotrun);
        robotAnimation = (AnimationDrawable) robotImageView.getBackground();
        robotImageView.setX(-500);
        robotImageView.setY((float)(height*.01));

        // Set Monster Animation, make it bigger if level 7
        if (level == 7) {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) monsterImageView.getLayoutParams();
            params.width *= 1.25;
            params.height *= 1.25;
        }
        monsterImageView.setY((float)(height*.01));
        monsterImageView.setX((float)(width*.05));
        setMonsterAnimation();

        // Start animations and translations
        boyAnimation.start();
        robotAnimation.start();
        monsterAnimation.start();
        boyImageView.animate().translationXBy((float)(width*.35)).setDuration(3000);
        robotImageView.animate().translationXBy((float)(width*.35)).setDuration(3000);

        // Set Boy to idle after he runs on screen
        boyImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                boyAnimation.stop();
                boyImageView.setBackgroundResource(R.drawable.boyidle);
                boyAnimation = (AnimationDrawable) boyImageView.getBackground();
                boyAnimation.start();
            }
        }, 3000);

        // Set Robot to Idle after he runs on screen
        robotImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                robotAnimation.stop();
                robotImageView.setBackgroundResource(R.drawable.robotidle);
                robotAnimation = (AnimationDrawable) robotImageView.getBackground();
                robotAnimation.start();
            }
        }, 3000);
    }

    /******************************************
     *   EXIT GAME
     *   Stop animations and move to Main Activity
     ******************************************/
    public void exitGame(View view) {
        boyAnimation.stop();
        monsterAnimation.stop();
        robotAnimation.stop();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /******************************************
     *   BATTLE
     *   Stop animations and move on to battle screen
     ******************************************/
    public void battle(View view) {
        boyAnimation.stop();
        monsterAnimation.stop();
        robotAnimation.stop();
        Intent intent = new Intent(this, BattleScreenActivity.class);
        startActivity(intent);

        // SFX Implementation
        switch (view.getId()) {
            case R.id.battleButton:
                soundPool.play(sound06, 1, 1, 0, 0, 1);
                break;
        }
    }

    /******************************************
     *   SET MONSTER ANIMATION
     *   Set the monster animation to idle
     ******************************************/
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

    /******************************************
     *   ON STOP
     ******************************************/
    @Override
    protected void onStop() {
        super.onStop();
        boyAnimation.stop();
        monsterAnimation.stop();
        robotAnimation.stop();
    }
}