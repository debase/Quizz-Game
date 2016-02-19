package etiennedebas.com.quizgame;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.Arrays;

/**
 * Created by etien on 12/02/2016.
 */
public class ProfileHelper {

    private static String PREFERENCES_KEY = "quizz_preferences_key";
    private static String ACTIVE_PROFILE_KEY = "quizz_preferences_key";
    private static String USER_PROFILES_KEY = "user_profiles_key";
    private static ProfileHelper mProfilPreferences;
    private UserProfile[] mUserProfiles;
    private UserProfile mCurrentActiveProfile;

    private ProfileHelper(Context context) {
        retrieveUserProfiles(context);
    }

    public static ProfileHelper getInstance(Context context) {
        if (mProfilPreferences == null) {
            mProfilPreferences = new ProfileHelper(context);

            /* Retrieve active user saved */
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
            String json = sharedPreferences.getString(ACTIVE_PROFILE_KEY, null);
            if (json != null) {
                Gson gson = new Gson();
                UserProfile userProfile = gson.fromJson(json, UserProfile.class);
                mProfilPreferences.setCurrentActiveProfile(userProfile);
                mProfilPreferences.commit(context);
            }

        }
        return mProfilPreferences;
    }

    private UserProfile[] retrieveUserProfiles(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(USER_PROFILES_KEY, "");
        try {
            mUserProfiles = gson.fromJson(json, UserProfile[].class);
            return mUserProfiles;
        } catch (JsonSyntaxException ex) {
            return null;
        }
    }

    public void addProfile(Context context, UserProfile userProfile) throws Exception {
        if (mUserProfiles == null) {
            mUserProfiles = new UserProfile[1];
        } else {
            mUserProfiles = Arrays.copyOf(mUserProfiles, mUserProfiles.length + 1);
        }
        mUserProfiles[mUserProfiles.length - 1] = userProfile;
    }

    public void commit(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();

        if (mUserProfiles != null) {
            String jsonProfiles = gson.toJson(mUserProfiles);
            prefsEditor.putString(USER_PROFILES_KEY, jsonProfiles);
        }

        if (mCurrentActiveProfile != null) {
            String jsonActiveProfile = gson.toJson(mCurrentActiveProfile);
            prefsEditor.putString(ACTIVE_PROFILE_KEY, jsonActiveProfile);
        } else {
            prefsEditor.remove(ACTIVE_PROFILE_KEY);
        }

        prefsEditor.apply();
    }

    public UserProfile findUserProfil(String uuid) {
        if (mUserProfiles != null) {
            for (UserProfile userProfile : mUserProfiles) {
                if (userProfile.isUUID(uuid)) {
                    return userProfile;
                }
            }
        }
        return null;
    }

    public UserProfile getCurrentActiveProfile() {
        return mCurrentActiveProfile;
    }

    public void setCurrentActiveProfile(UserProfile activeProfile) {
        if (activeProfile == null) {
            mCurrentActiveProfile = null;
            return;
        }

        for (UserProfile userProfile : mUserProfiles) {
            if (userProfile.equals(activeProfile)) {
                mCurrentActiveProfile = userProfile;
            }
        }
    }

    public UserProfile[] getUserProfiles() {
        return mUserProfiles;
    }

    public boolean isEmptyProfiles() {
        return mUserProfiles == null || mUserProfiles.length == 0;
    }

    public void removeProfile(UserProfile userProfile) {
        if (mUserProfiles != null) {
            UserProfile[] newUserProfiles = new UserProfile[mUserProfiles.length - 1];
            for (int i = 0, j = 0; i < mUserProfiles.length; i++) {
                UserProfile userProfileTmp = mUserProfiles[i];
                if (!userProfileTmp.equals(userProfile)) {
                    newUserProfiles[j] = userProfileTmp;
                    j++;
                }
            }
            mUserProfiles = newUserProfiles;
        }
    }
}
