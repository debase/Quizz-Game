package etiennedebas.com.quizgame.model;

import android.content.Context;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import etiennedebas.com.quizgame.QuizzHelper;
import etiennedebas.com.quizgame.R;
import etiennedebas.com.quizgame.fragment.CheckBoxValueSelectionFragment;
import etiennedebas.com.quizgame.fragment.GroupLayoutSelectionFragment;
import etiennedebas.com.quizgame.fragment.RadioGroupSelectionFragment;
import etiennedebas.com.quizgame.fragment.SeekBarValueSelectionFragment;
import etiennedebas.com.quizgame.fragment.TextValueSelectionFragment;
import etiennedebas.com.quizgame.fragment.TrueFalseSelectionFragment;

/**
 * Created by etien on 12/02/2016.
 */
public class ScienceNatureQuizz implements QuizzContent {

    private final ArrayList<QuizzQuestion> mQuizzQuestions;

    public ScienceNatureQuizz(Context context) {

        mQuizzQuestions = new ArrayList<>();
        List<String> mAnswer, mPossibleAnswer;
        String question;

        String[] questions = context.getResources().getStringArray(R.array.science_nature_question);

        /* QUESTION 1 */
        question = questions[0];
        mAnswer = QuizzHelper.getAnswersFromResources(context, R.string.science_nature_answer_0);
        mPossibleAnswer  = QuizzHelper.getPossibleAnswersFromResources(context, R.string.science_nature_answer_0);
        mQuizzQuestions.add(new QuizzQuestion(question, mPossibleAnswer, mAnswer) {
            @Override
            public Fragment getQuizzFragment() {
                return new TextValueSelectionFragment();
            }
        });

        /* QUESTION 2 */
        question = questions[1];
        mAnswer = QuizzHelper.getAnswersFromResources(context, R.string.science_nature_answer_1);
        mPossibleAnswer  = QuizzHelper.getPossibleAnswersFromResources(context, R.string.science_nature_answer_1);
        mQuizzQuestions.add(new QuizzQuestion(question, mPossibleAnswer, mAnswer) {
            @Override
            public Fragment getQuizzFragment() {
                return new RadioGroupSelectionFragment();
            }
        });

        /* QUESTION 3 */
        question = questions[2];
        mAnswer = QuizzHelper.getAnswersFromResources(context, R.string.science_nature_answer_2);
        mPossibleAnswer  = QuizzHelper.getPossibleAnswersFromResources(context, R.string.science_nature_answer_2);
        mQuizzQuestions.add(new QuizzQuestion(question, mPossibleAnswer, mAnswer) {
            @Override
            public Fragment getQuizzFragment() {
                return new GroupLayoutSelectionFragment();
            }
        });

        /* QUESTION 4 */
        question = questions[3];
        mAnswer = QuizzHelper.getAnswersFromResources(context, R.string.science_nature_answer_3);
        mPossibleAnswer  = QuizzHelper.getPossibleAnswersFromResources(context, R.string.science_nature_answer_3);
        mQuizzQuestions.add(new QuizzQuestion(question, mPossibleAnswer, mAnswer) {
            @Override
            public Fragment getQuizzFragment() {
                return new CheckBoxValueSelectionFragment();
            }
        });

        /* QUESTION 5 */
        question = questions[4];
        mAnswer = QuizzHelper.getAnswersFromResources(context, R.string.science_nature_answer_4);
        mPossibleAnswer  = QuizzHelper.getPossibleAnswersFromResources(context, R.string.science_nature_answer_4);
        mQuizzQuestions.add(new QuizzQuestion(question, mPossibleAnswer, mAnswer) {
            @Override
            public Fragment getQuizzFragment() {
                return new TrueFalseSelectionFragment();
            }
        });

        /* QUESTION 6 */
        question = questions[5];
        mAnswer = QuizzHelper.getAnswersFromResources(context, R.string.science_nature_answer_5);
        mPossibleAnswer  = QuizzHelper.getPossibleAnswersFromResources(context, R.string.science_nature_answer_5);
        mQuizzQuestions.add(new QuizzQuestion(question, mPossibleAnswer, mAnswer) {
            @Override
            public Fragment getQuizzFragment() {
                return new TextValueSelectionFragment();
            }
        });

        /* QUESTION 7 */
        question = questions[6];
        mAnswer = QuizzHelper.getAnswersFromResources(context, R.string.science_nature_answer_6);
        mPossibleAnswer  = QuizzHelper.getPossibleAnswersFromResources(context, R.string.science_nature_answer_6);
        mQuizzQuestions.add(new QuizzQuestion(question, mPossibleAnswer, mAnswer) {
            @Override
            public Fragment getQuizzFragment() {
                return new SeekBarValueSelectionFragment();
            }
        });

        /* QUESTION 8 */
        question = questions[7];
        mAnswer = QuizzHelper.getAnswersFromResources(context, R.string.science_nature_answer_7);
        mPossibleAnswer  = QuizzHelper.getPossibleAnswersFromResources(context, R.string.science_nature_answer_7);
        mQuizzQuestions.add(new QuizzQuestion(question, mPossibleAnswer, mAnswer) {
            @Override
            public Fragment getQuizzFragment() {
                return new SeekBarValueSelectionFragment();
            }
        });

        /* QUESTION 9 */
        question = questions[8];
        mAnswer = QuizzHelper.getAnswersFromResources(context, R.string.science_nature_answer_8);
        mPossibleAnswer  = QuizzHelper.getPossibleAnswersFromResources(context, R.string.science_nature_answer_8);
        mQuizzQuestions.add(new QuizzQuestion(question, mPossibleAnswer, mAnswer) {
            @Override
            public Fragment getQuizzFragment() {
                return new GroupLayoutSelectionFragment();
            }
        });

        /* QUESTION 10 */
        question = questions[9];
        mAnswer = QuizzHelper.getAnswersFromResources(context, R.string.science_nature_answer_9);
        mPossibleAnswer  = QuizzHelper.getPossibleAnswersFromResources(context, R.string.science_nature_answer_9);
        mQuizzQuestions.add(new QuizzQuestion(question, mPossibleAnswer, mAnswer) {
            @Override
            public Fragment getQuizzFragment() {
                return new TextValueSelectionFragment();
            }
        });
    }

    @Override
    public List<QuizzQuestion> getQuizzQuestions() {
        return mQuizzQuestions;
    }

    @Override
    public String getQuizzName(Context context) {
        return context.getString(R.string.science_and_nature);
    }
}
