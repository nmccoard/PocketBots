package com.example.pocketbots;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pocketbots.QuizContract.*;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BattleScreenQuestions.db";
    // Change the database version when you make changes to the database, like adding questions.
    private static final int DATABASE_VERSION = 3;

    private SQLiteDatabase db;

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREAT_QUESTION_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NUM + " INTEGER, " +
                QuestionsTable.COLUMN_LEVEL_ID + " INTEGER" +
                ")";

        db.execSQL(SQL_CREAT_QUESTION_TABLE);
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This function will delete our table and rebuilds it when the Database version number changes.
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTable() {
        // this is where we add the questions. Its currently filled with dummy questions for testing.
        Question q1 = new Question("Level 1 - A is correct", "A", "B", "C", "D", 1, 1);
        addQuestion(q1);
        Question q2 = new Question("Level 1 - B is correct", "A", "B", "C", "D", 2, 1);
        addQuestion(q2);
        Question q3 = new Question("Level 2 - C is correct", "A", "B", "C", "D", 3, 2);
        addQuestion(q3);
        Question q4 = new Question("Level 2 - D is correct", "A", "B", "C", "D", 4, 2);
        addQuestion(q4);
        Question q5 = new Question("Level 2 - Test question, filling out the TextView, A is the correct answer", "A", "B", "C", "D", 1, 2);
        addQuestion(q5);
        Question q6 = new Question("Level 1 - Test question, I really trying to filling out the TextView but not sure if it worked, A is the correct answer", "A", "B", "C", "D", 1, 1);
        addQuestion(q6);
        Question q7 = new Question("Level 2 - A is correct", "Correct", "Wrong", "Wrong", "Wrong", 1, 2);
        addQuestion(q7);
        Question q8 = new Question("Level 2 - B is correct", "A", "B", "C", "D", 2, 2);
        addQuestion(q8);
        Question q9 = new Question("Level 1 - C is correct", "Not this one", "or this one", "Pick Me", "but not me", 3, 1);
        addQuestion(q9);
        Question q10 = new Question("Level 1 - D is correct", "A", "B", "C", "D", 4, 1);
        addQuestion(q10);
        Question q11 = new Question("Level 1 - Test question, filling out the TextView, A is the correct answer", "A", "B", "C", "D", 1, 1);
        addQuestion(q11);
        Question q12 = new Question("Level 2 - Test question, I really trying to filling out the TextView but not sure if it worked, A is the correct answer", "A", "B", "C", "D", 1, 2);
        addQuestion(q12);
    }

    private void addQuestion (Question question) {
        // This function inserts the question into our questions table
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_OPTION4, question.getOption4());
        cv.put(QuestionsTable.COLUMN_ANSWER_NUM, question.getAnswerNum());
        cv.put(QuestionsTable.COLUMN_LEVEL_ID, question.getLevelID());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                question.setAnswerNum(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NUM)));
                question.setLevelID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_LEVEL_ID)));
                questionList.add(question);
            } while(c.moveToNext());
        }

        c.close();
        return questionList;
    }

    public List<Question> getQuestions(int  lvl) {
        String level = Integer.toString(lvl);
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String[] selectionArgs = new String[]{level};
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME +
                " WHERE " + QuestionsTable.COLUMN_LEVEL_ID + " = ?", selectionArgs);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                question.setAnswerNum(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NUM)));
                question.setLevelID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_LEVEL_ID)));
                questionList.add(question);
            } while(c.moveToNext());
        }

        c.close();
        return questionList;
    }
}