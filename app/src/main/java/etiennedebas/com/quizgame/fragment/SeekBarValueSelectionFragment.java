package etiennedebas.com.quizgame.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import etiennedebas.com.quizgame.QuizzListener;
import etiennedebas.com.quizgame.R;
import etiennedebas.com.quizgame.model.QuizzQuestion;

public class SeekBarValueSelectionFragment extends Fragment implements QuizzFragment<String> {

    private QuizzListener mListener;
    private QuizzQuestion mQuizzQuestion;
    private SeekBar mSeekBar;
    private TextView mSeekBarText;
    private int mSeekMin, mSeekMax;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_seekbar_value_selection, container, false);
        mQuizzQuestion = mListener.getCurrentQuestion();
        mSeekBar = (SeekBar) v.findViewById(R.id.seekbar);
        mSeekBarText = (TextView) v.findViewById(R.id.seekbar_value);

        List mSeekMinMax = mQuizzQuestion.getPossibleAnswer();
        mSeekMin = Integer.valueOf((String) mSeekMinMax.get(0));
        mSeekMax = Integer.valueOf((String) mSeekMinMax.get(1));

        mSeekBarText.setText(String.format("%d", mSeekMin));
        mSeekBar.setMax(mSeekMax - mSeekMin);
        mSeekBar.setProgress(0);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mSeekBarText.setText(String.format("%d", progress + mSeekMin));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

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
        return Arrays.asList(Integer.toString(mSeekBar.getProgress() + mSeekMin));
    }
}
