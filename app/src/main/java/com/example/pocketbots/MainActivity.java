
package com.example.pocketbots;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public SharedPreferences gameSettings;
    Button loadGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameSettings = getSharedPreferences("GameSettings", Context.MODE_PRIVATE);
        loadGame = findViewById(R.id.loadGameBtn);

        if(gameSettings.getInt("level", 0) == 0) {
            loadGame.setVisibility(View.GONE);
        }

    }

    public void newGame(View view) {
        // loadGame.setVisibility(View.GONE);
        // add option to ask for name
        // add another button to call another function to start the map activity
        SharedPreferences.Editor editGame = gameSettings.edit();
        editGame.putInt("level", 1);
        editGame.putInt("currentLevel", 1);
        editGame.commit();
      
        //Intent intent = new Intent(MainActivity.this, MapViewActivity.class);
        Intent intent = new Intent(MainActivity.this, EndingActivity.class);
        startActivity(intent);
    }

    public void loadGame(View view) {
        Intent intent = new Intent(this, MapViewActivity.class);
        startActivity(intent);
    }
}
