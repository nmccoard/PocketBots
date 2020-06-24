package com.example.pocketbots;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
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
    private TextView textViewOpponentHP;
    private TextView textViewPlayerHP;
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
    public AnimationDrawable redMonsterIdleAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_screen);

        gameSettings = getSharedPreferences("GameSettings", Context.MODE_PRIVATE);
        editGame = gameSettings.edit();
        level = gameSettings.getInt("level", 0);

       // Intent intent = getIntent();
        //level = intent.getStringExtra(MainActivity.EXTRA_LEVEL);

        boyImageView = (ImageView) findViewById(R.id.boyImageView);
        boyImageView.setBackgroundResource(R.drawable.boyidle);
        boyAnimation = (AnimationDrawable) boyImageView.getBackground();
        boyAnimation.start();

        monsterImageView = (ImageView) findViewById(R.id.monsterImageView);
        monsterImageView.setBackgroundResource(R.drawable.redmonsteridle);
        redMonsterIdleAnimation = (AnimationDrawable) monsterImageView.getBackground();
        redMonsterIdleAnimation.start();

        textViewQuestion = findViewById(R.id.TextView_Question);
        textViewOpponentHP = findViewById(R.id.opponentHPTextView);
        textViewPlayerHP = findViewById(R.id.playerHPTextView);
        rbGroup = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.radioButton1);
        rb2 = findViewById(R.id.radioButton2);
        rb3 = findViewById(R.id.radioButton3);
        rb4 = findViewById(R.id.radioButton4);
        submitBTN = findViewById(R.id.SubmitBTN);

        opponentHP = 10;
        textViewOpponentHP.setText("HP: " + opponentHP);
        textViewPlayerHP.setText("HP: " + playerHP);

        textColorDefaultRb = rb1.getTextColors();

        QuizDbHelper dbHelper = new QuizDbHelper(this);
        questionList = dbHelper.getQuestions(level);
        Collections.shuffle(questionList);

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
        if (opponentHP > 0) {
            currentQuestion = questionList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());
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
            opponentHP = opponentHP - (r.nextInt(6 - 2) + 2);
            textViewOpponentHP.setText("HP: " +opponentHP);
            // call the player's battle animation
        } else {
            // Reduce player HP a set amount
            playerHP = playerHP - 5;
            textViewPlayerHP.setText("HP: " + playerHP);
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
        if(opponentHP > 0){
            submitBTN.setText("Next");
        } else {
            submitBTN.setText("Finish");
        }
    }

    //This make it so it goes back to mainActivity
    private void finishQuiz() {
        level++;
        editGame.putInt("level", level);
        editGame.commit();
        Log.d("Level", "Next level is " + level);
        //Intent intent = new Intent(this, MapViewActivity.class);
        Intent intent = new Intent(this, BattleIntroActivity.class);
        startActivity(intent);
        //finish();
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
}