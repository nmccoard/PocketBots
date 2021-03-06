
package com.example.pocketbots;

import android.provider.BaseColumns;

/******************************************
 *   QuizContract
 *   Setting up the database constant variables to make building and pulling from the db easier
 ******************************************/
public final class QuizContract {

    private QuizContract() {}

    public static class QuestionsTable {

        public static final String TABLE_NAME = "battle_screen_questions";
        public static final String _ID = "_id";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_OPTION4 = "option4";
        public static final String COLUMN_ANSWER_NUM = "answer_num";
        public static final String COLUMN_LEVEL_ID = "level_id";
    }
}