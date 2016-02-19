package etiennedebas.com.quizgame;

import etiennedebas.com.quizgame.model.QuizzContent;
import etiennedebas.com.quizgame.model.QuizzQuestion;

/**
 * Created by etien on 10/02/2016.
 */
public interface QuizzListener {
    void nextQuestion();
    QuizzQuestion getCurrentQuestion();
    QuizzContent getQuizzContent();

}
