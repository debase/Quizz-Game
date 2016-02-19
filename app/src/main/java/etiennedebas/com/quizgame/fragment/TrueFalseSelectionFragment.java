package etiennedebas.com.quizgame.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import etiennedebas.com.quizgame.QuizzListener;
import etiennedebas.com.quizgame.R;
import etiennedebas.com.quizgame.model.QuizzQuestion;

public class TrueFalseSelectionFragment extends Fragment implements QuizzFragment<String>, View.OnClickListener {

    private QuizzListener mListener;
    private QuizzQuestion mQuizzQuestion;
    private Boolean selection = null;
    CardView trueButton, falseButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_true_false_selection, container, false);

        trueButton = (CardView) v.findViewById(R.id.true_card);
        falseButton = (CardView) v.findViewById(R.id.false_card);

        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);

        return v;
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
    @Override
    public List<String> getSelections() {
        if (selection == null) {
            return null;
        }
        return Arrays.asList(selection.toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.true_card:
                trueButton.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.true_color));
                falseButton.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                selection = true;
                break;
            case R.id.false_card:
                falseButton.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRedDark));
                trueButton.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                selection = false;
                break;
        }
    }
}
