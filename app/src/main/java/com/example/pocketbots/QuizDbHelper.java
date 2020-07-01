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
    private static final int DATABASE_VERSION = 12;

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
        Question q1 = new Question("What set of brackets do we use to mark the beginning and end of a block of code?",  "{}", "[]", "()", "<>", 1, 1);
        addQuestion(q1);
        Question q2 = new Question("Every line of code must run in a _____.", "constructor", "class", "function", "block of code {}", 2, 1);
        addQuestion(q2);
        Question q3 = new Question( "Do classes start with an uppercase letter?", "Sometimes", "Never", "Most of the time", "Always", 4, 1);
        addQuestion(q3);
        Question q4 = new Question( "The name of the java file must match the class name.", "Most of the time", "Sometimes", "Always", "Never", 3, 1);
        addQuestion(q4);
        Question q5 = new Question( "What does the \"println( )\" method do?", "prints data", "stores data", "deletes data", "recovers data", 1, 1);
        addQuestion(q5);
        Question q6 = new Question( "What method must be included in every java program?", "system()", "start()", "main()", "java()", 3, 1);
        addQuestion(q6);
        Question q7 = new Question( "Each code statement must end with a _____.", ":", ".", "}", ";", 4, 1);
        addQuestion(q7);
        Question q8 = new Question( "What is the correct syntax for multi-line comments?", "// comment", "/* comment */", "<!-- comment -->", "# comment", 2, 1);
        addQuestion(q8);
        Question q9 = new Question( "Any text after // or between /* and */ are _____ by Java.", "executed", "ignored", "compiled", "printed on screen", 2, 1);
        addQuestion(q9);
        Question q10 = new Question( "What line of code will print \"So\" to the screen?", "out.println(\"So\");", "println(\"So\");", "System.out.println(\"So\")", "System.println(\"So\")", 3, 1);
        addQuestion(q10);
        Question q11 = new Question( "What is a String? A variable that stores _____.", "text", "letters", "symbols", "numbers", 1, 2);
        addQuestion(q11);
        Question q12 = new Question( "What is the best example of an integer?", "\"Five\"", "5", "0.5", "5e32", 2, 2);
        addQuestion(q12);
        Question q13 = new Question( "What is the best example of a Float?", "19", "\"Nineteen\"", "1.9", "1.9e42", 3, 2);
        addQuestion(q13);
        Question q14 = new Question( "What can be represented by a char type variable in single quotes?", "A letter", "A symbol", "A number", "All answers are true", 4, 2);
        addQuestion(q14);
        Question q15 = new Question( "A boolean is a variable that can store a _____ state.", "static", "dynamic", "program", "true and a false", 4, 2);
        addQuestion(q15);
        Question q16 = new Question( "Which is not a primitive data type?", "long", "short", "String", "byte", 3, 2);
        addQuestion(q16);
        Question q17 = new Question( "Which is a non-primitive data type?", "Classes", "Arrays", "String", "All answers are true", 4, 2);
        addQuestion(q17);
        Question q18 = new Question( "Which is not an integer type?", "float", "byte", "short", "long", 1, 2);
        addQuestion(q18);
        Question q19 = new Question( "Which is a floating point type?", "int", "double", "short", "long", 2, 2);
        addQuestion(q19);
        Question q20 = new Question( "A scientific number can be represented with _____?", "only a float", "only a double", "both a float and a double", "only a long", 3, 2);
        addQuestion(q20);
        Question q21 = new Question( "What is the value of sum2? int sum1 = 100 + 50; int sum2 = sum1 + 250;", "\"sum1250\"", "350", "400", "150", 3, 3);
        addQuestion(q21);
        Question q22 = new Question( "What does the modulus (%) operator do?", "Multiplies 2 values", "Adds 2 values", "Divides 2 values", "Returns the remainder", 4, 3);
        addQuestion(q22);
        Question q23 = new Question( "What does the Increment (++) operator do?", "Decrease by 1", "Increase by 1", "Returns the remainder", "Adds 2 values", 2, 3);
        addQuestion(q23);
        Question q24 = new Question( "What does the Decrement (--) operator do?", "Decrease by 1", "Increase by 1", "Subtracts 2 values", "Divides 2 values", 1, 3);
        addQuestion(q24);
        Question q25 = new Question( "All assignment operators perform _____", "arithmetic", "storing a value", "comparisons", "logical operations", 2, 3);
        addQuestion(q25);
        Question q26 = new Question( "Which expression is true?", "9.5 <= 9", "4 != 4", "2 >= 2", "5 < 5", 3, 3);
        addQuestion(q26);
        Question q27 = new Question( "Which expression is false?", "5 == 6", "7 > 6", "3 <=3", "1 != 9", 1, 3);
        addQuestion(q27);
        Question q28 = new Question( "Which is not a logical operator?", "&&", "||", "!", "=", 4, 3);
        addQuestion(q28);
        Question q29 = new Question( "Which is not an assignment operator?", "<<=", "&=", "|=", "==", 4, 3);
        addQuestion(q29);
        Question q30 = new Question( "What is x equal to after the following code is executed? int x = 2; if (x=3) {x=4;} ", "0", "4", "3", "2", 2, 3);
        addQuestion(q30);
        Question q31 = new Question( "Which method will find the highest value of x and y? Example: Math._____(x, y);", "max", "min", "maximum", "abs", 1, 4);
        addQuestion(q31);
        Question q32 = new Question( "Which method will find the square root of x? Example: Math._____(x);", "root", "squareRoot", "sq", "sqrt", 4, 4);
        addQuestion(q32);
        Question q33 = new Question( "Which method will return a random number between 0 and 1? Example: Math.______;", "rand()", "rand(0, 1)", "random()", "random(0, 1)", 3, 4);
        addQuestion(q33);
        Question q34 = new Question( "The syntax to access the Math class is? Example: _____.max(x, y);", "Mathematics", "Math", "MathClass", "MathLib", 2, 4);
        addQuestion(q34);
        Question q35 = new Question( "Which method will find the lowest value of x and y? Example: Math._____(x, y);", "max", "lowest", "minimum", "min", 4, 4);
        addQuestion(q35);
        Question q36 = new Question( "Which method will find the absolute value? Example: Math._____(-1.7);", "absolute", "abs", "absoluteValue", "value", 2, 4);
        addQuestion(q36);
        Question q37 = new Question( "Fill in the blank to return a random number between 0 and 100? Example: (int)(Math._____);", "random() * 100", "random(0, 101)", "random() * 101", "random(0, 100)", 3, 4);
        addQuestion(q37);
        Question q38 = new Question( "Math.min(6, 8); will return which value?", "6", "8", "true", "null", 1, 4);
        addQuestion(q38);
        Question q39 = new Question( "Math.abs(-2.3); will return which value?", "-2.3", "false", "true", "2.3", 4, 4);
        addQuestion(q39);
        Question q40 = new Question( "Math.sqrt(49); will return which value?", "7", "49", "-7", "-49", 1, 4);
        addQuestion(q40);
        Question q41 = new Question( "Which are possible values for a boolean?", "true, false, null", "1, 0", "true, false", "true, false, the set of all integers", 3, 5);
        addQuestion(q41);
        Question q42 = new Question( "What is the default value for a boolean?", "null", "0", "false", "true", 3, 5);
        addQuestion(q42);
        Question q43 = new Question( "What is the missing syntax for initializing the boolean variable? Example: _____ isJavaFun = true;", "boolean", "lean", "TorF", "bool", 1, 5);
        addQuestion(q43);
        Question q44 = new Question( "Which expression will print the value true? Example: System.out.println(_____);", "!true", "5 > 4", "8 == 9", "3 < 2", 2, 5);
        addQuestion(q44);
        Question q45 = new Question( "Which expression will print the value false? Example: System.out.println(_____);", "7 == 7", "9 > 2", "!false", "4 < 1", 4, 5);
        addQuestion(q45);
        Question q46 = new Question( "What is the output of the following line of code? System.out.println(11 == 20);", "20", "11", "true", "false", 4, 5);
        addQuestion(q46);
        Question q47 = new Question( "What is the output of the following line of code? System.out.println(12 > 10);", "false", "true", "12", "10", 2, 5);
        addQuestion(q47);
        Question q48 = new Question( "What is the output of the following line of code? System.out.println(9 < 3);", "false", "true", "3", "9", 1, 5);
        addQuestion(q48);
        Question q49 = new Question( "What is the output of the following line of code? System.out.println(13 == 13);", "null", "13", "true", "false", 3, 5);
        addQuestion(q49);
        Question q50 = new Question( "What is the output of the following line of code? Int x = 5; System.out.println(x < 7);", "7", "true", "x", "false", 2, 5);
        addQuestion(q50);
        Question q51 = new Question( "What is the syntax for a string? Example: _____ text = \"Pocketbots\";", "String", "Text", "Char", "Letters", 1, 6);
        addQuestion(q51);
        Question q52 = new Question( "What is the output of the following line of code? String txt = \"Pocketbots\"; System.out.println(txt.length());", "txt", "10", "Pocketbots", "3", 2, 6);
        addQuestion(q52);
        Question q53 = new Question( "What is the output of the following line of code? String txt = \"PoCkEtBoTs\"; System.out.println(txt.toLowerCase());", "PoCkEtBoTs", "txt", "pocketbots", "tolowercase", 3, 6);
        addQuestion(q53);
        Question q54 = new Question( "What is the output of the following line of code? String txt = \"PoCkEtBoTs\"; System.out.println(txt.toUpperCase());", "PoCkEtBoTs", "TXT", "TOUPPERCASE", "POCKETBOTS", 4, 6);
        addQuestion(q54);
        Question q55 = new Question( "What is the output of the following line of code? String txt = \"This game is fun\"; System.out.println(txt.indexOf(\"game\")); ", "6", "game", "txt", "5", 4, 6);
        addQuestion(q55);
        Question q56 = new Question( "What is the output of the following line of code? String txt = \"Bots\"; System.out.println(\"Pocket\" + txt);", "Pockettxt", "Pocket Bots", "PocketBots", "Pocket txt", 3, 6);
        addQuestion(q56);
        Question q57 = new Question("What is the output of the following line of code? System.out.println(“We are \\“Bots\\””);", "We are \\Bots\\", "We are “Bots”", "We are \\”Bots\\”", "Bots", 2, 6);
        addQuestion(q57);
        Question q58 = new Question("What is the output of the following line of code? System.out.println(“I\\’m 1\\\\2 bot”);", "I’m 1\\2 bot", "I\\’m 1\\\\2 bot", "I’m 1\\\\2 bot", "I\\’m 1\\2 bot", 1, 6);
        addQuestion(q58);
        Question q59 = new Question( "Which is not a valid escape sequence?", "\\d", "\\n", "\\t", "\\b", 1, 6);
        addQuestion(q59);
        Question q60 = new Question( "What is the output of the following line of code? System.out.println(\"10\" + \"30\");", "40", "10 30", "1030", "\"10\" \"30\"", 3, 6);
        addQuestion(q60);

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