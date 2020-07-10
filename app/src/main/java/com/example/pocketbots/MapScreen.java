package com.example.pocketbots;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MapScreen extends AppCompatActivity {
    private Button lvl1Btn;
    private Button lvl2Btn;
    private Button lvl3Btn;
    private Button lvl4Btn;
    private Button lvl5Btn;
    private Button lvl6Btn;
    private Button lvl7Btn;

    private int level;
    private int currentLevel;

    public SharedPreferences gameSettings;
    public SharedPreferences.Editor editGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_screen);

        lvl1Btn = (Button) findViewById(R.id.lvl1Btn);
        lvl2Btn = (Button) findViewById(R.id.lvl2Btn);
        lvl3Btn = (Button) findViewById(R.id.lvl3Btn);
        lvl4Btn = (Button) findViewById(R.id.lvl4Btn);
        lvl5Btn = (Button) findViewById(R.id.lvl5Btn);
        lvl6Btn = (Button) findViewById(R.id.lvl6Btn);
        lvl7Btn = (Button) findViewById(R.id.lvl7Btn);

        // Check current level
        gameSettings = getSharedPreferences("GameSettings", Context.MODE_PRIVATE);
        editGame = gameSettings.edit();
        currentLevel = gameSettings.getInt("currentLevel", 1);

        switch (currentLevel){
            case 1:
                lvl1Btn.setVisibility(View.VISIBLE);
                lvl2Btn.setVisibility(View.INVISIBLE);
                lvl3Btn.setVisibility(View.INVISIBLE);
                lvl4Btn.setVisibility(View.INVISIBLE);
                lvl5Btn.setVisibility(View.INVISIBLE);
                lvl6Btn.setVisibility(View.INVISIBLE);
                lvl7Btn.setVisibility(View.INVISIBLE);
                break;
            case 2:
                lvl1Btn.setVisibility(View.VISIBLE);
                lvl2Btn.setVisibility(View.VISIBLE);
                lvl3Btn.setVisibility(View.INVISIBLE);
                lvl4Btn.setVisibility(View.INVISIBLE);
                lvl5Btn.setVisibility(View.INVISIBLE);
                lvl6Btn.setVisibility(View.INVISIBLE);
                lvl7Btn.setVisibility(View.INVISIBLE);
                break;
            case 3:
                lvl1Btn.setVisibility(View.VISIBLE);
                lvl2Btn.setVisibility(View.VISIBLE);
                lvl3Btn.setVisibility(View.VISIBLE);
                lvl4Btn.setVisibility(View.INVISIBLE);
                lvl5Btn.setVisibility(View.INVISIBLE);
                lvl6Btn.setVisibility(View.INVISIBLE);
                lvl7Btn.setVisibility(View.INVISIBLE);
                break;
            case 4:
                lvl1Btn.setVisibility(View.VISIBLE);
                lvl2Btn.setVisibility(View.VISIBLE);
                lvl3Btn.setVisibility(View.VISIBLE);
                lvl4Btn.setVisibility(View.VISIBLE);
                lvl5Btn.setVisibility(View.INVISIBLE);
                lvl6Btn.setVisibility(View.INVISIBLE);
                lvl7Btn.setVisibility(View.INVISIBLE);
                break;
            case 5:
                lvl1Btn.setVisibility(View.VISIBLE);
                lvl2Btn.setVisibility(View.VISIBLE);
                lvl3Btn.setVisibility(View.VISIBLE);
                lvl4Btn.setVisibility(View.VISIBLE);
                lvl5Btn.setVisibility(View.VISIBLE);
                lvl6Btn.setVisibility(View.INVISIBLE);
                lvl7Btn.setVisibility(View.INVISIBLE);
                break;
            case 6:
                lvl1Btn.setVisibility(View.VISIBLE);
                lvl2Btn.setVisibility(View.VISIBLE);
                lvl3Btn.setVisibility(View.VISIBLE);
                lvl4Btn.setVisibility(View.VISIBLE);
                lvl5Btn.setVisibility(View.VISIBLE);
                lvl6Btn.setVisibility(View.VISIBLE);
                lvl7Btn.setVisibility(View.INVISIBLE);
                break;
            case 7:
                lvl1Btn.setVisibility(View.VISIBLE);
                lvl2Btn.setVisibility(View.VISIBLE);
                lvl3Btn.setVisibility(View.VISIBLE);
                lvl4Btn.setVisibility(View.VISIBLE);
                lvl5Btn.setVisibility(View.VISIBLE);
                lvl6Btn.setVisibility(View.VISIBLE);
                lvl7Btn.setVisibility(View.VISIBLE);
                break;
        }

    }
    public void btn1OnClick(View view) {
        editGame.putInt("level", 1);
        editGame.commit();
        Intent intent = new Intent(this, BattleIntroActivity.class);
        startActivity(intent);
    }

    public void btn2OnClick(View view) {
        editGame.putInt("level", 2);
        editGame.commit();
        Intent intent = new Intent(this, BattleIntroActivity.class);
        startActivity(intent);
    }

    public void btn3OnClick(View view) {
        editGame.putInt("level", 3);
        editGame.commit();
        Intent intent = new Intent(this, BattleIntroActivity.class);
        startActivity(intent);
    }

    public void btn4OnClick(View view) {
        editGame.putInt("level", 4);
        editGame.commit();
        Intent intent = new Intent(this, BattleIntroActivity.class);
        startActivity(intent);
    }

    public void btnO5nClick(View view) {
        editGame.putInt("level", 5);
        editGame.commit();
        Intent intent = new Intent(this, BattleIntroActivity.class);
        startActivity(intent);
    }

    public void btn6OnClick(View view) {
        editGame.putInt("level", 6);
        editGame.commit();
        Intent intent = new Intent(this, BattleIntroActivity.class);
        startActivity(intent);
    }

    public void btn7OnClick(View view) {
        editGame.putInt("level", 7);
        editGame.commit();
        Intent intent = new Intent(this, BattleIntroActivity.class);
        startActivity(intent);
    }
}

