package etiennedebas.com.quizgame.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import etiennedebas.com.quizgame.ProfileHelper;
import etiennedebas.com.quizgame.R;
import etiennedebas.com.quizgame.UserProfile;

public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ProfileHelper profileHelper = ProfileHelper.getInstance(this);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearlayout);

        List<UserProfile> userProfileList = new ArrayList<>(Arrays.asList(profileHelper.getUserProfiles()));
        Collections.sort(userProfileList, new Comparator<UserProfile>() {
            public int compare(UserProfile user1, UserProfile user2) {
                return user1.getTotalScore() > user2.getTotalScore() ? -1 : 1;
            }
        });

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < userProfileList.size(); i++) {
            UserProfile userProfile = userProfileList.get(i);
            View v = layoutInflater.inflate(R.layout.item_leaderboard, null);
            ((TextView) v.findViewById(R.id.position)).setText(String.format("%d", i));
            ((ImageView) v.findViewById(R.id.user_avatar)).setImageResource(userProfile.getUserImageRes());
            ((TextView) v.findViewById(R.id.user_name)).setText(String.format("%s %s", userProfile.getUserFirstName(), userProfile.getUserLastInitial()));
            ((TextView) v.findViewById(R.id.user_score)).setText(String.format("%d", userProfile.getTotalScore()));
            linearLayout.addView(v, i);
        }
    }

    public static void start(Context context) {
        Intent leaderBoardIntent = new Intent(context, LeaderboardActivity.class);
        context.startActivity(leaderBoardIntent);
    }
}
