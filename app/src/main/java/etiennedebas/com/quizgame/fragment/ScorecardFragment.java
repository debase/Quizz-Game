package etiennedebas.com.quizgame.fragment;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import etiennedebas.com.quizgame.QuizzListener;
import etiennedebas.com.quizgame.R;
import etiennedebas.com.quizgame.model.QuizzContent;
import etiennedebas.com.quizgame.model.QuizzQuestion;

public class ScorecardFragment extends Fragment {

    private QuizzListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scorecard, container, false);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearlayout);

        QuizzContent quizzContent = mListener.getQuizzContent();
        List<QuizzQuestion> quizzQuestions = quizzContent.getQuizzQuestions();

        for (QuizzQuestion quizzQuestion : quizzQuestions) {
            View v = inflater.inflate(R.layout.item_score_card, null);
            ImageView successImage = (ImageView) v.findViewById(R.id.iconSuccess);

            // Set success or fail image
            boolean success = quizzQuestion.getSuccess();
            successImage.setImageDrawable(setTintImage(success ? R.drawable.check : R.drawable.cross, success ? R.color.true_color : R.color.false_color));

            // Set question text
            ((TextView) v.findViewById(R.id.question_text)).setText(quizzQuestion.getQuestion());

            // loop for display ansers
            List<String> answers = quizzQuestion.getAnswers();
            TextView answersText = (TextView) v.findViewById(R.id.responseText);
            answersText.setText("");
            for (String answer : answers) {
                answersText.append(answer + "\n");
            }

            linearLayout.addView(v);
        }

        return view;
    }

    public Drawable setTintImage(int drawableRes, int colorRes) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), drawableRes);
        int color = ContextCompat.getColor(getContext(), colorRes);
        DrawableCompat.setTint(drawable, color);
        return drawable;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof QuizzListener) {
            mListener = (QuizzListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
