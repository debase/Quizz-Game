package etiennedebas.com.quizgame.model;

import android.content.Context;

import java.util.List;

/**
 * Created by etien on 10/02/2016.
 */
public interface QuizzContent {
    List<QuizzQuestion> getQuizzQuestions();
    public String getQuizzName(Context context);
}
