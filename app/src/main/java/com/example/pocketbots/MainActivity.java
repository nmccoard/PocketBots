
package com.example.pocketbots;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_LEVEL = "com.example.pocketbots.LEVEL";
    public SharedPreferences gameSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences  gameSettings = getSharedPreferences("GameSettings", MODE_PRIVATE);

    }

    public void newGame(View view) {

        SharedPreferences.Editor editGame = gameSettings.edit();
        editGame.putInt("level", 1);
        Intent intent = new Intent(MainActivity.this, BattleIntroActivity.class);
        startActivity(intent);
    }

    public void loadGame(View view) {
        Intent intent = new Intent(this, BattleIntroActivity.class);
        startActivity(intent);
    }
}
