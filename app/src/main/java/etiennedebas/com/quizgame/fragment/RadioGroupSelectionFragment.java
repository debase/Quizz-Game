package etiennedebas.com.quizgame.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import etiennedebas.com.quizgame.QuizzListener;
import etiennedebas.com.quizgame.R;
import etiennedebas.com.quizgame.model.QuizzQuestion;

/**
 * Created by etien on 10/02/2016.
 */
public class RadioGroupSelectionFragment extends Fragment implements QuizzFragment {

    private QuizzListener mListener;
    private QuizzQuestion mQuizzQuestion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.radio_group_quizz, container, false);
        mQuizzQuestion = mListener.getCurrentQuestion();

        RadioGroup radioGroup = (RadioGroup) v.findViewById(R.id.radiogroup);

        if (mQuizzQuestion != null) {
            List possibleAnswer = mQuizzQuestion.getPossibleAnswer();
            RadioGroup.LayoutParams radioLayoutParams = new RadioGroup.LayoutParams(getContext(), null);
            radioLayoutParams.setMargins(0, 10, 0, 10);
            for (Object s : possibleAnswer) {
                RadioButton radioButton = new RadioButton(getContext());
                radioButton.setLayoutParams(radioLayoutParams);
                radioButton.setText(s.toString());
                radioGroup.addView(radioButton);
            }
        }

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
        List<String> selections = new ArrayList<>();

        if (getView() != null) {
            RadioGroup radioGroup = (RadioGroup) getView().findViewById(R.id.radiogroup);
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                if (radioGroup.getChildAt(i) instanceof RadioButton) {
                    RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                    Log.d("RadioFragment", radioButton.toString() + " " + radioButton.isChecked());
                    if (radioButton.isChecked()) {
                        selections.add(radioButton.getText().toString());
                    }
                }
            }
        }
        return selections;
    }
}
