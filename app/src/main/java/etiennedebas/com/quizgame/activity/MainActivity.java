package etiennedebas.com.quizgame.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import etiennedebas.com.quizgame.ProfileHelper;
import etiennedebas.com.quizgame.R;
import etiennedebas.com.quizgame.UserProfile;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Random random = new Random();
    private int mLayouytId[] = new int[4];
    private Handler handler = new Handler();
    private boolean mAnimationCard = true;
    private ProfileHelper mProfileHelper;
    private UserProfile mCurrentUserProfile;
    private ImageView mUserImageView;
    private TextView mUserName;
    private TextView mUserScore;

    private static final int REQUEST_CODE_PROFILE = 1;
    private static final int REQUEST_CODE_CHOOSE_PROFILE = 2;

    private Runnable runnableCode = new Runnable() {
        @Override
        public void run() {
            Animation bounce = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.bounce);
            findViewById(mLayouytId[random.nextInt(4)]).startAnimation(bounce);

            if (mAnimationCard) {
                handler.postDelayed(runnableCode, 5000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProfileHelper = ProfileHelper.getInstance(this);
        if (mProfileHelper.getCurrentActiveProfile() == null) {
            Intent intent = new Intent(this, ChooseProfilActivity.class);
            startActivityForResult(intent, REQUEST_CODE_CHOOSE_PROFILE);
        } else {
            mCurrentUserProfile = mProfileHelper.getCurrentActiveProfile();
        }

        setContentView(R.layout.activity_main);

        mLayouytId[0] = R.id.layout_food;
        mLayouytId[1] = R.id.layout_history;
        mLayouytId[2] = R.id.layout_science;
        mLayouytId[3] = R.id.layout_tvmovies;

        findViewById(R.id.layout_food).setOnClickListener(this);
        findViewById(R.id.layout_history).setOnClickListener(this);
        findViewById(R.id.layout_science).setOnClickListener(this);
        findViewById(R.id.layout_tvmovies).setOnClickListener(this);
        findViewById(R.id.layout_leaderboard).setOnClickListener(this);

        this.mUserImageView = (ImageView) findViewById(R.id.user_avatar);
        this.mUserName = (TextView) findViewById(R.id.user_name);
        this.mUserScore = (TextView) findViewById(R.id.user_score);

        this.mUserImageView.setOnClickListener(this);

        setUpView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_PROFILE) {
            if (resultCode == ProfilActivity.USER_UPDATE_PROFILE){
                setUpView();
            } else if (resultCode == ProfilActivity.USER_LOGOUT || resultCode == ProfilActivity.USER_DELETE_ACCOUNT) {
                Intent intent = new Intent(this, ChooseProfilActivity.class);
                startActivityForResult(intent, REQUEST_CODE_CHOOSE_PROFILE);
            }
        }

        if (requestCode == REQUEST_CODE_CHOOSE_PROFILE) {
            if (resultCode == Activity.RESULT_CANCELED) {
                finish();
            } else if (resultCode == Activity.RESULT_OK){
                mCurrentUserProfile = mProfileHelper.getCurrentActiveProfile();
                setUpView();
            }
        }
    }

    private void setUpView() {
        if (mCurrentUserProfile != null) {
            this.mUserImageView.setImageResource(mCurrentUserProfile.getUserImageRes());
            this.mUserName.setText(String.format("%s %s", mCurrentUserProfile.getUserFirstName(), mCurrentUserProfile.getUserLastInitial()));
            this.mUserScore.setText(String.format("%d", mCurrentUserProfile.getTotalScore()));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_food:
                QuizzActivity.start(this, QuizzActivity.QuizzType.FOOD_DRINK_QUIZZ, mCurrentUserProfile.getUserImageRes());
                break;
            case R.id.layout_tvmovies:
                QuizzActivity.start(this, QuizzActivity.QuizzType.TV_MOVIES_QUIZZ, mCurrentUserProfile.getUserImageRes());
                break;
            case R.id.layout_science:
                QuizzActivity.start(this, QuizzActivity.QuizzType.SCIENCE_NATURE_QUIZZ, mCurrentUserProfile.getUserImageRes());
                break;
            case R.id.layout_history:
                QuizzActivity.start(this, QuizzActivity.QuizzType.HISTORY_QUIZZ, mCurrentUserProfile.getUserImageRes());
                break;
            case R.id.layout_leaderboard:
                LeaderboardActivity.start(this);
                break;
            case R.id.user_avatar:
                ProfilActivity.startActivtyForResult(this, REQUEST_CODE_PROFILE);
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAnimationCard = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAnimationCard = true;
        handler.post(runnableCode);
        setUpView();
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
