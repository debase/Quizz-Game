package etiennedebas.com.quizgame.model;

import android.support.v4.app.Fragment;

import java.util.Arrays;
import java.util.List;

/**
 * Created by etien on 10/02/2016.
 */
public abstract class QuizzQuestion {
    private List<String> answers;
    private String question;
    private List<String> possibleAnswer;
    private boolean success;

    private int defaultScore;

    public QuizzQuestion(String question, String answer) {
        this(question, Arrays.asList(answer), Arrays.asList(answer));
    }

    public QuizzQuestion(String question, List<String> possibleAnswer, String answer) {
        this(question, possibleAnswer, Arrays.asList(answer));
    }

    public QuizzQuestion(String question, List<String> possibleAnswer, List<String> answers) {
        this.answers = answers;
        this.question = question;
        this.possibleAnswer = possibleAnswer;
        this.success = false;
        this.defaultScore = 8;
    }

    public boolean checkAnswers(List<String> selectedValue) {
        if (selectedValue == null || selectedValue.isEmpty()) {
            return false;
        }
        return selectedValue.containsAll(answers);
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<String> getPossibleAnswer() {
        return possibleAnswer;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public java.lang.String getQuestion() {
        return question;
    }

    public boolean isMultipleChoise() {
        return answers.size() > 1;
    }

    public int getScore(List<String> selectedValues) {
        return checkAnswers(selectedValues) ? defaultScore : 0;
    }

    public abstract Fragment getQuizzFragment();

    public boolean getSuccess() {
        return success;
    }
}
