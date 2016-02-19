package etiennedebas.com.quizgame.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import etiennedebas.com.quizgame.QuizzListener;
import etiennedebas.com.quizgame.R;
import etiennedebas.com.quizgame.model.QuizzQuestion;

/**
 * Created by etien on 10/02/2016.
 */
public class CheckBoxValueSelectionFragment extends Fragment implements QuizzFragment {

    private QuizzListener mListener;
    private QuizzQuestion mQuizzQuestion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_check_box_value_selection, container, false);
        mQuizzQuestion = mListener.getCurrentQuestion();

        LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.linearlayout);

        if (mQuizzQuestion != null) {
            List possibleAnswer = mQuizzQuestion.getPossibleAnswer();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 10, 0, 10);
            for (Object s : possibleAnswer) {
                CheckBox checkBox = new CheckBox(getContext());
                checkBox.setText(s.toString());
                linearLayout.addView(checkBox, params);
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
            LinearLayout linearLayout = (LinearLayout) getView().findViewById(R.id.linearlayout);
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                if (linearLayout.getChildAt(i) instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) linearLayout.getChildAt(i);
                    if (checkBox.isChecked()) {
                        selections.add(checkBox.getText().toString());
                    }
                }
            }
        }
        return selections;
    }
}
