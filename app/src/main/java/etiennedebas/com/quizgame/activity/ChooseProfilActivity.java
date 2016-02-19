package etiennedebas.com.quizgame.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import etiennedebas.com.quizgame.ProfileHelper;
import etiennedebas.com.quizgame.R;
import etiennedebas.com.quizgame.UserProfile;

public class ChooseProfilActivity extends AppCompatActivity implements ViewPropertyAnimatorListener, View.OnClickListener {

    private final int REQUEST_CODE_PROFILE = 0;

    private ImageView iconTopicImage;

    int currentPos = 0;
    private boolean hasScaledOut;
    private ProfileHelper mProfileHelper;
    private static int[] iconTopicRes = new int[] {
            R.drawable.icon_food,
            R.drawable.icon_history,
            R.drawable.icon_sciences,
            R.drawable.icon_tvmovies
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProfileHelper = ProfileHelper.getInstance(this);

        setContentView(R.layout.activity_choose_profil);

        iconTopicImage = (ImageView) findViewById(R.id.icon_image);

        hasScaledOut = false;

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearlayout);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        UserProfile[] userProfileList = mProfileHelper.getUserProfiles();
        if (userProfileList != null) {
            for (UserProfile userProfile : userProfileList) {
                View v = layoutInflater.inflate(R.layout.item_profil_chooser, null);
                ((ImageView) v.findViewById(R.id.user_avatar)).setImageResource(userProfile.getUserImageRes());
                ((TextView) v.findViewById(R.id.user_name)).setText(String.format("%s %s", userProfile.getUserFirstName(), userProfile.getUserLastInitial()));
                v.setOnClickListener(this);
                v.setTag(userProfile);
                linearLayout.addView(v);
            }
        }

        // add create profil view
        View createProfile = layoutInflater.inflate(R.layout.item_profil_chooser, null);
        ((ImageView) createProfile.findViewById(R.id.user_avatar)).setImageResource(R.drawable.avatar_new);
        ((TextView) createProfile.findViewById(R.id.user_name)).setText(String.format("%s", getString(R.string.create_profile)));
        createProfile.setOnClickListener(this);
        linearLayout.addView(createProfile);


        animateIcon(iconTopicImage);
    }

    private void animateIcon(ImageView iconTopicImage) {

        float scale;

        if (hasScaledOut) {
            currentPos++;
            if (currentPos >= iconTopicRes.length) {
                currentPos = 0;
            }
            iconTopicImage.setImageResource(iconTopicRes[currentPos]);
            scale = 1.0f;
            hasScaledOut = false;
        } else {
            hasScaledOut = true;
            scale = 0f;
        }

        ViewCompat.animate(iconTopicImage).scaleY(scale).scaleX(scale).setDuration(800).setListener(this);
    }

    @Override
    public void onAnimationStart(View view) {

    }

    @Override
    public void onAnimationEnd(View view) {
        animateIcon(iconTopicImage);
    }

    @Override
    public void onAnimationCancel(View view) {

    }

    @Override
    public void onClick(View v) {
        if (v.getTag() instanceof UserProfile) {
            UserProfile userProfile = (UserProfile) v.getTag();

            mProfileHelper.setCurrentActiveProfile(userProfile);
            mProfileHelper.commit(this);

            setResult(Activity.RESULT_OK);

            finish();
        } else {
            ProfilActivity.startActivtyForResult(this, REQUEST_CODE_PROFILE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PROFILE) {
            if (resultCode == ProfilActivity.USER_CREATE_PROFILE) {
                UserProfile userProfile = (UserProfile) data.getSerializableExtra(UserProfile.EXTRA_USER);
                ProfileHelper.getInstance(this).setCurrentActiveProfile(userProfile);
                setResult(Activity.RESULT_OK);
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }
}
