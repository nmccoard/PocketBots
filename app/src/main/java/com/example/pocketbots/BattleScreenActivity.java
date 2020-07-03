package com.example.pocketbots;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BattleScreenActivity extends AppCompatActivity {
    private TextView textViewQuestion;
    //private TextView textViewOpponentHP;
    //private TextView textViewPlayerHP;
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button submitBTN;

    private int level;

    private int opponentHP;
    private int playerHP = 10;
    private int questionCounter;

    private ColorStateList textColorDefaultRb;

    private List<Question> questionList;
    private Question currentQuestion;

    private boolean answered;

    private long backPressedTime;

    public SharedPreferences gameSettings;
    public SharedPreferences.Editor editGame;

    public ImageView boyImageView;
    public AnimationDrawable boyAnimation;
    public ImageView monsterImageView;
    public AnimationDrawable monsterAnimation;

    public ImageView playerHealth;
    public ImageView monsterHealth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_screen);

        // Check the current level
        gameSettings = getSharedPreferences("GameSettings", Context.MODE_PRIVATE);
        editGame = gameSettings.edit();
        level = gameSettings.getInt("level", 0);

        // Player and Opponent Images and Animations
        boyImageView = (ImageView) findViewById(R.id.boyImageView);
        boyImageView.setBackgroundResource(R.drawable.boyidle);
        boyAnimation = (AnimationDrawable) boyImageView.getBackground();
        boyAnimation.start();
        monsterImageView = (ImageView) findViewById(R.id.monsterImageView);
        if (level == 7) {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) monsterImageView.getLayoutParams();
            params.width *= 1.25;
            params.height *= 1.25;
        }
        setMonsterAnimation();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        // 720 x 1440 - Brian
        // 1080 x 2220 - Nate
        // 1,440 x 3,040 - Briana // 1080 x 2047
        Log.d("Dimensions", "Width: " + width + " Height: " + height);
        if(width < 1740 ) {
            boyImageView.setY((float)(height*.054));
            //boyImageView.setX((float)(width*.025));
            monsterImageView.setY((float)(height*.054));
            //monsterImageView.setX((float)(width*.05));
        } else {
            boyImageView.setY((float)(height*.007));
            //boyImageView.setX((float)(width*.025));
            monsterImageView.setY((float)(height*.007));
            //monsterImageView.setX((float)(width*.05));
        }


        // Set Health Points and images
        playerHealth = (ImageView) findViewById(R.id.playerHealth);
        monsterHealth = (ImageView) findViewById(R.id.monsterHealth);
        playerHealth.setBackgroundResource(R.drawable.health10);
        monsterHealth.setBackgroundResource(R.drawable.health10);
        //textViewOpponentHP.setText("HP: " + opponentHP);
        //textViewPlayerHP.setText("HP: " + playerHP);

        textViewQuestion = findViewById(R.id.TextView_Question);
        //textViewOpponentHP = findViewById(R.id.opponentHPTextView);
        //textViewPlayerHP = findViewById(R.id.playerHPTextView);
        rbGroup = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.radioButton1);
        rb2 = findViewById(R.id.radioButton2);
        rb3 = findViewById(R.id.radioButton3);
        rb4 = findViewById(R.id.radioButton4);
        submitBTN = findViewById(R.id.SubmitBTN);

        textColorDefaultRb = rb1.getTextColors();

        QuizDbHelper dbHelper = new QuizDbHelper(this);
        if(level == 7){
            // sets the monsters hp higher and pulls questions from every level
            opponentHP = 10;
            questionList = dbHelper.getAllQuestions();
        } else {
            opponentHP = 10;
            questionList = dbHelper.getQuestions(level);
        }
        Collections.shuffle(questionList);

        //textViewOpponentHP.setText("HP: " + opponentHP);
        //textViewPlayerHP.setText("HP: " + playerHP);
        showNextQuestion();

        submitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
                        checkAnswer();
                    } else {
                        Toast.makeText(BattleScreenActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNextQuestion();
                }
            }
        });
    }

    private void showNextQuestion(){
        // rest the text color back to default
        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);
        rb4.setTextColor(textColorDefaultRb);

        // Clear selection
        rbGroup.clearCheck();

        // ask another question
        if (opponentHP > 0 && playerHP > 0) {
            currentQuestion = questionList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());
            Log.d("Question", "The current question is: " + currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());

            questionCounter++;
            answered = false;
            submitBTN.setText("Submit");
        } else {
            finishQuiz();
        }
    }

    private void checkAnswer() {
        answered = true;
        Random r = new Random();

        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNum = rbGroup.indexOfChild(rbSelected) + 1;

        if (answerNum == currentQuestion.getAnswerNum()){
            // reduces opponents HP by a random amount between 2-5.
            if(level == 7){
                opponentHP = opponentHP - 1;
            } else {
                opponentHP = opponentHP - (r.nextInt(6 - 2) + 2);
            }
            if (opponentHP < 0) {
                opponentHP = 0;
            }
            setHealth(monsterHealth, opponentHP);
            //textViewOpponentHP.setText("HP: " +opponentHP);
            // call the player's battle animation
        } else {
            // Reduce player HP a set amount
            playerHP = playerHP - 5;
            Log.d("HP", "The Monsters HP: " + opponentHP + " The Players HP is " + playerHP);
            if(playerHP <= 0){
                Toast.makeText(this, "Your Pocketbot was DEFEATED :(", Toast.LENGTH_LONG).show();
                playerHP = 0;
            }
            setHealth(playerHealth, playerHP);
            //textViewPlayerHP.setText("HP: " + playerHP);
            // else call the opponents battle animation
        }

        showSolution();
    }

    private void showSolution() {
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);
        rb4.setTextColor(Color.RED);

        switch (currentQuestion.getAnswerNum()) {
            case 1:
                rb1.setTextColor(Color.GREEN);
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                break;
            case 4:
                rb4.setTextColor(Color.GREEN);
                break;
        }

        // these action will need to be called after the fight animation
        if(opponentHP > 0) {
            submitBTN.setText("Next");
            if(playerHP <= 0){
                submitBTN.setText("Finish");
            }
        } else {
            submitBTN.setText("Finish");
            win();
            level++;
            editGame.putInt("level", level);
            editGame.commit();
            Log.d("Level", "Next level is " + level);
        }
    }

    //This make it so it goes back to mainActivity
    private void finishQuiz() {
        if(level == 8) {
            editGame.putInt("level", 0);
            editGame.commit();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            //Intent intent = new Intent(this, MapViewActivity.class);
            Intent intent = new Intent(this, BattleIntroActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed(){
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            finishQuiz();
        } else {
            Toast.makeText(this, "Press back again to runaway form the fight", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
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
        monsterAnimation.start();
    }

    public void win() {
        switch(level) {
            case 1:
                monsterImageView.setBackgroundResource(R.drawable.redhit);
                break;
            case 2:
                monsterImageView.setBackgroundResource(R.drawable.skullhit);
                break;
            case 3:
                monsterImageView.setBackgroundResource(R.drawable.bluehit);
                break;
            case 4:
                monsterImageView.setBackgroundResource(R.drawable.greyhit);
                break;
            case 5:
                monsterImageView.setBackgroundResource(R.drawable.grhit);
                break;
            case 6:
                monsterImageView.setBackgroundResource(R.drawable.pinkhit);
                break;
            case 7:
                monsterImageView.setBackgroundResource(R.drawable.orangehit);
                break;
        }
        monsterAnimation = (AnimationDrawable) monsterImageView.getBackground();
        monsterAnimation.start();
    }

    public void setHealth(ImageView healthBar, int health) {

        switch(health) {
            case 0:
                healthBar.setBackgroundResource(R.drawable.health0);
                break;
            case 1:
                healthBar.setBackgroundResource(R.drawable.health1);
                break;
            case 2:
                healthBar.setBackgroundResource(R.drawable.health2);
                break;
            case 3:
                healthBar.setBackgroundResource(R.drawable.health3);
                break;
            case 4:
                healthBar.setBackgroundResource(R.drawable.health4);
                break;
            case 5:
                healthBar.setBackgroundResource(R.drawable.health5);
                break;
            case 6:
                healthBar.setBackgroundResource(R.drawable.health6);
                break;
            case 7:
                healthBar.setBackgroundResource(R.drawable.health7);
                break;
            case 8:
                healthBar.setBackgroundResource(R.drawable.health8);
                break;
            case 9:
                healthBar.setBackgroundResource(R.drawable.health9);
                break;
            default:
                healthBar.setBackgroundResource(R.drawable.health10);
        }
    }

}