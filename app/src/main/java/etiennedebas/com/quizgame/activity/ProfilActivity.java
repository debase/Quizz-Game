package etiennedebas.com.quizgame.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;
import etiennedebas.com.quizgame.ProfileHelper;
import etiennedebas.com.quizgame.R;
import etiennedebas.com.quizgame.UserProfile;

public class ProfilActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    public static final int USER_LOGOUT = 1;
    public static final int USER_DELETE_ACCOUNT = 2;
    public static final int USER_UPDATE_PROFILE = 3;
    public static final int USER_CREATE_PROFILE = 4;

    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    private UserProfile mCurrentProfile;
    private TextView mFirstName;
    private TextView mLastInital;
    private int mCurrentAvatarPos;
    private ProfileHelper mProfileHelper;
    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab_create_profile);
        this.mFloatingActionButton.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mProfileHelper = ProfileHelper.getInstance(this);

        mCurrentProfile = ProfileHelper.getInstance(this).getCurrentActiveProfile();

        if (mCurrentProfile == null) {
            getSupportActionBar().setTitle(R.string.create_profile);
        } else {
            getSupportActionBar().setTitle(R.string.profile);
        }

        this.mFirstName = (TextView) findViewById(R.id.first_name);
        this.mLastInital = (TextView) findViewById(R.id.last_initial);

        this.mFirstName.addTextChangedListener(this);
        this.mLastInital.addTextChangedListener(this);

        this.mCurrentAvatarPos = -1;

        if (mCurrentProfile != null) {
            mFirstName.setText(mCurrentProfile.getUserFirstName());
            mLastInital.setText(mCurrentProfile.getUserLastInitial());
            mCurrentAvatarPos = mCurrentProfile.getUserImagePos();
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.avatar_grid);
        int childCount = gridLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (gridLayout.getChildAt(i) instanceof CircleImageView) {
                if (i == mCurrentAvatarPos) {
                    CircleImageView circleImageView = (CircleImageView) gridLayout.getChildAt(i);
                    circleImageView.setBorderColor(ContextCompat.getColor(this, R.color.colorGreenDark));
                    circleImageView.setBorderWidth(2);
                    updateFloatingButton();
                }
                gridLayout.getChildAt(i).setOnClickListener(this);
            }
        }

        updateFloatingButton();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.fab_create_profile) {

            int resultCode = mCurrentProfile == null ? USER_CREATE_PROFILE : USER_UPDATE_PROFILE;

            if (mCurrentProfile == null) {
                mCurrentProfile = new UserProfile();
                try {
                    mProfileHelper.addProfile(this, mCurrentProfile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            mCurrentProfile.setUserFirstName(mFirstName.getText().toString());
            mCurrentProfile.setUserLastInitial(mLastInital.getText().toString());
            mCurrentProfile.setUserImagePos(mCurrentAvatarPos);

            mProfileHelper.commit(this);

            Intent returnIntent = new Intent();
            returnIntent.putExtra(UserProfile.EXTRA_USER, mCurrentProfile);
            setResult(resultCode, returnIntent);
            finish();

        } else {
            changeUserSelection(v);
        }

    }

    private void changeUserSelection(View v) {
        GridLayout gridLayout = (GridLayout) findViewById(R.id.avatar_grid);
        int gridChildCount = gridLayout.getChildCount();
        for (int i = 0; i < gridChildCount; i++ ) {
            if (gridLayout.getChildAt(i) instanceof CircleImageView) {
                CircleImageView circleImageView = (CircleImageView) gridLayout.getChildAt(i);
                if (circleImageView == v) {
                    circleImageView.setBorderColor(ContextCompat.getColor(this, R.color.colorGreenDark));
                    circleImageView.setBorderWidth(2);
                    mCurrentAvatarPos = i;
                    updateFloatingButton();
                } else {
                    circleImageView.setBorderWidth(0);
                }
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        updateFloatingButton();
    }

    public void updateFloatingButton() {
        if (mFirstName.getText().toString().trim().length() > 0
                && mLastInital.getText().toString().trim().length() > 0
                && mCurrentAvatarPos >= 0) {
            mFloatingActionButton.setVisibility(View.VISIBLE);
            ViewCompat.animate(mFloatingActionButton).scaleX(1.0F).scaleY(1.0F).alpha(1.0F)
                    .setInterpolator(INTERPOLATOR).withLayer().setListener(null)
                    .start();
        } else {
            ViewCompat.animate(mFloatingActionButton).scaleX(0.0F).scaleY(0.0F).alpha(0.0F).setInterpolator(INTERPOLATOR).withLayer()
                    .setListener(new ViewPropertyAnimatorListener() {
                        public void onAnimationStart(View view) {
                        }

                        public void onAnimationCancel(View view) {
                        }

                        public void onAnimationEnd(View view) {
                            view.setVisibility(View.GONE);
                        }
                    }).start();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (ProfileHelper.getInstance(this).getCurrentActiveProfile() != null) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.profil_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                performLogout();
                break;
            case R.id.delete_account:
                performDeleteAccount();
                break;
            case R.id.reset:
                mCurrentProfile.resetScores();
                mProfileHelper.commit(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void performDeleteAccount() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle(R.string.logout);
        adb.setMessage(R.string.confirm_logout);
        adb.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                mProfileHelper.removeProfile(mCurrentProfile);
                mProfileHelper.setCurrentActiveProfile(null);
                mProfileHelper.commit(ProfilActivity.this);

                setResult(USER_DELETE_ACCOUNT);
                finish();
            }
        });
        adb.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        adb.show();
    }

    private void performLogout() {
        mProfileHelper.setCurrentActiveProfile(null);
        mProfileHelper.commit(ProfilActivity.this);
        setResult(USER_LOGOUT);
        finish();
    }

    public static void startActivtyForResult(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, ProfilActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }
}
