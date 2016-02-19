package etiennedebas.com.quizgame.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import etiennedebas.com.quizgame.ProfileHelper;
import etiennedebas.com.quizgame.UserProfile;
import etiennedebas.com.quizgame.fragment.QuizzFragment;
import etiennedebas.com.quizgame.QuizzListener;
import etiennedebas.com.quizgame.R;
import etiennedebas.com.quizgame.fragment.ScorecardFragment;
import etiennedebas.com.quizgame.model.FoodDrinkQuizz;
import etiennedebas.com.quizgame.model.HistoryQuizz;
import etiennedebas.com.quizgame.model.QuizzContent;
import etiennedebas.com.quizgame.model.QuizzQuestion;
import etiennedebas.com.quizgame.model.ScienceNatureQuizz;
import etiennedebas.com.quizgame.model.TvMoviesQuizz;

public class QuizzActivity extends AppCompatActivity implements QuizzListener, View.OnClickListener {

    private static final String QUIZZ_FRAGMENT_TAG = "quizz_fragment";
    public static String EXTRA_QUIZZ_TYPE = "quizz_type";

    private QuizzContent mQuizzContent;
    private QuizzQuestion mCurrentQuestion;
    private int questionIndex;
    private ProgressBar progressBar;
    private FloatingActionButton mFloatingActionButton;
    private TextView mScoreTextView;
    private int mCurrentScore;
    private boolean quizzEnded;
    private UserProfile mUserProfile;
    private QuizzType mQuizzType;

    public enum QuizzType {
        HISTORY_QUIZZ,
        FOOD_DRINK_QUIZZ,
        SCIENCE_NATURE_QUIZZ,
        TV_MOVIES_QUIZZ
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = this.getIntent();
        mQuizzType = (QuizzType) intent.getSerializableExtra(EXTRA_QUIZZ_TYPE);
        mUserProfile = ProfileHelper.getInstance(this).getCurrentActiveProfile();

        switch (mQuizzType) {
            case HISTORY_QUIZZ:
                mQuizzContent = new HistoryQuizz(this);
                setTheme(R.style.HistoryTheme);
                break;
            case FOOD_DRINK_QUIZZ:
                mQuizzContent = new FoodDrinkQuizz(this);
                setTheme(R.style.FoodDrinkTheme);
                break;
            case SCIENCE_NATURE_QUIZZ:
                mQuizzContent = new ScienceNatureQuizz(this);
                setTheme(R.style.ScienceNatureTheme);
                break;
            case TV_MOVIES_QUIZZ:
                mQuizzContent = new TvMoviesQuizz(this);
                setTheme(R.style.TvMoviesTheme);
                break;
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quizz);

        if (mUserProfile != null) {
            ((ImageView) findViewById(R.id.avatar_profil)).setImageResource(mUserProfile.getUserImageRes());
        }

        ((TextView) findViewById(R.id.sectionText)).setText(mQuizzContent.getQuizzName(this));
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab_check);
        mScoreTextView = (TextView) findViewById(R.id.scoreText);
        mScoreTextView.setText("0");

        this.questionIndex = 0;
        this.mCurrentScore = 0;
        this.quizzEnded = false;

        findViewById(R.id.fab_check).setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        nextQuestion();
    }

    @Override
    public void nextQuestion() {
        final QuizzQuestion nextQuestion = getCurrentQuestion();

        if (mCurrentQuestion != null) {
            checkAnswer();
        }

        if (nextQuestion != null) {

            Handler handler = new Handler();
            mFloatingActionButton.setClickable(false);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    changeFABColorAndDrawable(R.drawable.check, R.color.white);

                    ((TextView) findViewById(R.id.question_text)).setText(nextQuestion.getQuestion());
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                            .replace(R.id.frame_layout, nextQuestion.getQuizzFragment(), QUIZZ_FRAGMENT_TAG)
                            .commit();

                    // change progress
                    int progress = (int) (((float)(questionIndex + 1) / (float)mQuizzContent.getQuizzQuestions().size()) * 100f);
                    changeProgress(progress);

                    // set current question index
                    ((TextView) findViewById(R.id.currentQuestionText))
                            .setText(String.format("%d / %d", questionIndex + 1, mQuizzContent.getQuizzQuestions().size()));

                    mCurrentQuestion = nextQuestion;

                    mFloatingActionButton.setClickable(true);

                }
            }, 500);


        } else if (!quizzEnded) {

            ((TextView) findViewById(R.id.question_text)).setText(getString(R.string.score_card));
            changeFABColorAndDrawable(R.drawable.check, R.color.white);
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                    .replace(R.id.frame_layout, new ScorecardFragment(), QUIZZ_FRAGMENT_TAG)
                    .commit();


            quizzEnded = true;

        } else {
            mUserProfile.setScore(mQuizzType, mCurrentScore);
            ProfileHelper.getInstance(this).commit(this);
            finish();
        }
    }

    private void changeProgress(int progress) {
        if(android.os.Build.VERSION.SDK_INT >= 11){
            ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", progress);
            animation.setDuration(500); // 0.5 second
            animation.setInterpolator(new LinearInterpolator());
            animation.start();
        }
        else {
            progressBar.setProgress(progress); // no animation on Gingerbread or lower
        }
    }

    private void checkAnswer() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(QUIZZ_FRAGMENT_TAG);
        if (currentFragment != null && currentFragment instanceof QuizzFragment) {
            QuizzFragment quizzFragment = (QuizzFragment) currentFragment;
            List selections = quizzFragment.getSelections();

            boolean success = false;
            if (selections != null) {
                success = mCurrentQuestion.checkAnswers(selections);
            }

            if (success) {
                mCurrentScore += mCurrentQuestion.getScore(selections);
                changeFABColorAndDrawable(R.drawable.good, R.color.true_color);
                mScoreTextView.setText(String.format("%d", mCurrentScore));
            } else {
                changeFABColorAndDrawable(R.drawable.cross, R.color.false_color);
            }

            mCurrentQuestion.setSuccess(success);
        }
    }

    private void changeFABColorAndDrawable(int drawableRes, int colorRes) {
        mFloatingActionButton.setImageResource(drawableRes);
        int color = ContextCompat.getColor(this, colorRes);
        mFloatingActionButton.setBackgroundTintList(ColorStateList.valueOf(color));
    }

    @Override
    public QuizzQuestion getCurrentQuestion() {
        List<QuizzQuestion> mQuizzQuestion = mQuizzContent.getQuizzQuestions();
        if (questionIndex < mQuizzQuestion.size()) {
            return mQuizzQuestion.get(questionIndex);
        }
        return null;
    }

    @Override
    public QuizzContent getQuizzContent() {
        return mQuizzContent;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab_check) {
            questionIndex++;
            nextQuestion();
        }
    }

    public static void start(Context context, QuizzType quizzType, int avatarRes) {
        Intent intent = new Intent(context, QuizzActivity.class);
        intent.putExtra(EXTRA_QUIZZ_TYPE, quizzType);
        context.startActivity(intent);
    }
}
