package etiennedebas.com.quizgame;

import android.content.Context;

import java.util.Arrays;
import java.util.List;

/**
 * Created by etien on 12/02/2016.
 */
public class QuizzHelper {

    private static enum QuizzQuestionContent {
        ANSWERS,
        POSSIBLE_ANSWER
    }

    private static List<String> getAnswersOrPossibles(Context context, QuizzQuestionContent quizzQuestionContent, int stringResId) {
        String answer = context.getResources().getString(stringResId);

        String[] array = answer.split(";");
        switch (quizzQuestionContent) {
            case ANSWERS:
                return Arrays.asList(array[0].split(","));
            case POSSIBLE_ANSWER:
                return Arrays.asList(array[array.length - 1].split(","));
        }

        return null;
    }

    public static List<String> getAnswersFromResources(Context context, int stringResId) {
        return getAnswersOrPossibles(context, QuizzQuestionContent.ANSWERS, stringResId);
    }

    public static List<String> getPossibleAnswersFromResources(Context context, int stringResId) {
        return getAnswersOrPossibles(context, QuizzQuestionContent.POSSIBLE_ANSWER, stringResId);
    }
}
