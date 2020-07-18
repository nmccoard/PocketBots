package com.example.pocketbots;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/******************************************
 *   MAP VIEW ACTIVITY
 *   The screen which allows the user to select the level that they wish to play.
 ******************************************/
public class MapViewActivity extends AppCompatActivity {

    private Button lvlBtn1;
    private Button lvlBtn2;
    private Button lvlBtn3;
    private Button lvlBtn4;
    private Button lvlBtn5;
    private Button lvlBtn6;
    private Button lvlBtn7;



    public ImageView bgImageView;

    public SharedPreferences gameSettings;
    public SharedPreferences.Editor editGame;

    public int level;
    public int currentLevel;

    private SoundPool soundPool;
    private int sound05;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        bgImageView = (ImageView) findViewById(R.id.bgImageView);

        gameSettings = getSharedPreferences("GameSettings", Context.MODE_PRIVATE);
        editGame = gameSettings.edit();
        level = gameSettings.getInt("level", 0);
        currentLevel = gameSettings.getInt("currentLevel", 1);

        lvlBtn1 = findViewById(R.id.lvlBtn1);
        lvlBtn2 = findViewById(R.id.lvlBtn2);
        lvlBtn3 = findViewById(R.id.lvlBtn3);
        lvlBtn4 = findViewById(R.id.lvlBtn4);
        lvlBtn5 = findViewById(R.id.lvlBtn5);
        lvlBtn6 = findViewById(R.id.lvlBtn6);
        lvlBtn7 = findViewById(R.id.lvlBtn7);

        switch (currentLevel){
            case 1:
                lvlBtn1.setVisibility(View.VISIBLE);
                lvlBtn2.setVisibility(View.INVISIBLE);
                lvlBtn3.setVisibility(View.INVISIBLE);
                lvlBtn4.setVisibility(View.INVISIBLE);
                lvlBtn5.setVisibility(View.INVISIBLE);
                lvlBtn6.setVisibility(View.INVISIBLE);
                lvlBtn7.setVisibility(View.INVISIBLE);
                break;
            case 2:
                lvlBtn1.setVisibility(View.VISIBLE);
                lvlBtn2.setVisibility(View.VISIBLE);
                lvlBtn3.setVisibility(View.INVISIBLE);
                lvlBtn4.setVisibility(View.INVISIBLE);
                lvlBtn5.setVisibility(View.INVISIBLE);
                lvlBtn6.setVisibility(View.INVISIBLE);
                lvlBtn7.setVisibility(View.INVISIBLE);
                break;
            case 3:
                lvlBtn1.setVisibility(View.VISIBLE);
                lvlBtn2.setVisibility(View.VISIBLE);
                lvlBtn3.setVisibility(View.VISIBLE);
                lvlBtn4.setVisibility(View.INVISIBLE);
                lvlBtn5.setVisibility(View.INVISIBLE);
                lvlBtn6.setVisibility(View.INVISIBLE);
                lvlBtn7.setVisibility(View.INVISIBLE);
                break;
            case 4:
                lvlBtn1.setVisibility(View.VISIBLE);
                lvlBtn2.setVisibility(View.VISIBLE);
                lvlBtn3.setVisibility(View.VISIBLE);
                lvlBtn4.setVisibility(View.VISIBLE);
                lvlBtn5.setVisibility(View.INVISIBLE);
                lvlBtn6.setVisibility(View.INVISIBLE);
                lvlBtn7.setVisibility(View.INVISIBLE);
                break;
            case 5:
                lvlBtn1.setVisibility(View.VISIBLE);
                lvlBtn2.setVisibility(View.VISIBLE);
                lvlBtn3.setVisibility(View.VISIBLE);
                lvlBtn4.setVisibility(View.VISIBLE);
                lvlBtn5.setVisibility(View.VISIBLE);
                lvlBtn6.setVisibility(View.INVISIBLE);
                lvlBtn7.setVisibility(View.INVISIBLE);
                break;
            case 6:
                lvlBtn1.setVisibility(View.VISIBLE);
                lvlBtn2.setVisibility(View.VISIBLE);
                lvlBtn3.setVisibility(View.VISIBLE);
                lvlBtn4.setVisibility(View.VISIBLE);
                lvlBtn5.setVisibility(View.VISIBLE);
                lvlBtn6.setVisibility(View.VISIBLE);
                lvlBtn7.setVisibility(View.INVISIBLE);
                break;
            case 7:
                lvlBtn1.setVisibility(View.VISIBLE);
                lvlBtn2.setVisibility(View.VISIBLE);
                lvlBtn3.setVisibility(View.VISIBLE);
                lvlBtn4.setVisibility(View.VISIBLE);
                lvlBtn5.setVisibility(View.VISIBLE);
                lvlBtn6.setVisibility(View.VISIBLE);
                lvlBtn7.setVisibility(View.VISIBLE);
                break;
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
        sound05 = soundPool.load(this, R.raw.sound05, 1);

    }

    public void level1(View view){
        editGame.putInt("level", 1);
        editGame.commit();
        Intent intent = new Intent(this, BattleIntroActivity.class);
        startActivity(intent);

        // SFX Implementation
        switch (view.getId()) {
            case R.id.lvlBtn1:
                soundPool.play(sound05, 1, 1, 0, 0, 1);
                break;
        }
    }

    public void level2(View view){
        editGame.putInt("level", 2);
        editGame.commit();
        Intent intent = new Intent(this, BattleIntroActivity.class);
        startActivity(intent);

        // SFX Implementation
        switch (view.getId()) {
            case R.id.lvlBtn2:
                soundPool.play(sound05, 1, 1, 0, 0, 1);
                break;
        }
    }

    public void level3(View view){
        editGame.putInt("level", 3);
        editGame.commit();
        Intent intent = new Intent(this, BattleIntroActivity.class);
        startActivity(intent);

        // SFX Implementation
        switch (view.getId()) {
            case R.id.lvlBtn3:
                soundPool.play(sound05, 1, 1, 0, 0, 1);
                break;
        }
    }

    public void level4(View view){
        editGame.putInt("level", 4);
        editGame.commit();
        Intent intent = new Intent(this, BattleIntroActivity.class);
        startActivity(intent);

        // SFX Implementation
        switch (view.getId()) {
            case R.id.lvlBtn4:
                soundPool.play(sound05, 1, 1, 0, 0, 1);
                break;
        }
    }

    public void level5(View view){
        editGame.putInt("level", 5);
        editGame.commit();
        Intent intent = new Intent(this, BattleIntroActivity.class);
        startActivity(intent);

        // SFX Implementation
        switch (view.getId()) {
            case R.id.lvlBtn5:
                soundPool.play(sound05, 1, 1, 0, 0, 1);
                break;
        }
    }

    public void level6(View view){
        editGame.putInt("level", 6);
        editGame.commit();
        Intent intent = new Intent(this, BattleIntroActivity.class);
        startActivity(intent);

        // SFX Implementation
        switch (view.getId()) {
            case R.id.lvlBtn6:
                soundPool.play(sound05, 1, 1, 0, 0, 1);
                break;
        }
    }

    public void level7(View view){
        editGame.putInt("level", 7);
        editGame.commit();
        Intent intent = new Intent(this, BattleIntroActivity.class);
        startActivity(intent);

        // SFX Implementation
        switch (view.getId()) {
            case R.id.lvlBtn7:
                soundPool.play(sound05, 1, 1, 0, 0, 1);
                break;
        }
    }

}