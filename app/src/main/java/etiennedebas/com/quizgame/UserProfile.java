package etiennedebas.com.quizgame;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import etiennedebas.com.quizgame.activity.QuizzActivity;

/**
 * Created by etien on 12/02/2016.
 */
public class UserProfile implements Serializable {

    public static final String EXTRA_USER = "extra_user";

    private Map<QuizzActivity.QuizzType, Integer> mScoreMap;
    private String mUserFirstName;
    private String mUserLastInitial;
    private int userImage;
    private String mUUID;

    private static int[] mAvatarsId = new int[] {
            R.drawable.avatar_1,
            R.drawable.avatar_2,
            R.drawable.avatar_3,
            R.drawable.avatar_4,
            R.drawable.avatar_5,
            R.drawable.avatar_6,
            R.drawable.avatar_7,
            R.drawable.avatar_8,
            R.drawable.avatar_9,
            R.drawable.avatar_10
    };

    public UserProfile() {
        this("", "", -1);
    }

    public UserProfile(String userFirstName, String userLastInitial, int userImage) {
        this.mUserFirstName = userFirstName;
        this.mUserLastInitial = userLastInitial;
        this.userImage = userImage;
        this.mScoreMap = new HashMap<>();
        this.mUUID = UUID.randomUUID().toString();
    }

    public int getTotalScore() {
        int totalScore = 0;
        for (Map.Entry<QuizzActivity.QuizzType, Integer> entry : mScoreMap.entrySet())
        {
            totalScore += entry.getValue();
        }
        return totalScore;
    }

    public void setScore(QuizzActivity.QuizzType quizz, int score) {
        this.mScoreMap.put(quizz, score);
    }

    public int getUserImageRes() {
        return mAvatarsId[userImage];
    }

    public int getUserImagePos() {
        return userImage;
    }

    public String getUserFirstName() {
        return mUserFirstName;
    }

    public void setUserFirstName(String mUserFirstName) {
        this.mUserFirstName = mUserFirstName;
    }

    public String getUserLastInitial() {
        return mUserLastInitial;
    }

    public void setUserLastInitial(String mUserLastInitial) {
        this.mUserLastInitial = mUserLastInitial;
    }

    public void setUserImagePos(int userImage) {
        this.userImage = userImage;
    }

    public void resetScores() {
        mScoreMap.clear();
    }

    public String getUUID() {
        return mUUID;
    }

    public boolean isUUID(String uuid) {
        return mUUID.equals(uuid);
    }

    public static int getAvatarID(int position) {
        return mAvatarsId[position];
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof UserProfile) {
            return ((UserProfile) o).mUUID.equals(mUUID);
        }
        return false;
    }
}
