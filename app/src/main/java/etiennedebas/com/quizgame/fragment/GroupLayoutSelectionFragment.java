package etiennedebas.com.quizgame.fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import etiennedebas.com.quizgame.QuizzListener;
import etiennedebas.com.quizgame.R;
import etiennedebas.com.quizgame.model.QuizzQuestion;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;

/**
 * Created by etien on 10/02/2016.
 */
public class GroupLayoutSelectionFragment extends Fragment implements QuizzFragment, View.OnClickListener {

    private QuizzListener mListener;
    private SupportAnimator mAnimator;
    private QuizzQuestion mQuizzQuestion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.group_layout_quizz, container, false);
        mQuizzQuestion = mListener.getCurrentQuestion();

        GridLayout gridLayout = (GridLayout) v.findViewById(R.id.gridLayout);
        if (mQuizzQuestion != null) {
            List possibleAnswer = mQuizzQuestion.getPossibleAnswer();
            for (int i = 0 ; i < gridLayout.getChildCount(); i++) {
                View parent = gridLayout.getChildAt(i);
                TextView textView = (TextView) parent.findViewById(R.id.text_item_grid);
                textView.setText(possibleAnswer.get(i).toString());
                parent.findViewById(R.id.item_grid_card).setTag(false);
                parent.findViewById(R.id.item_grid_card).setOnClickListener(this);
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
    public List getSelections() {
        List<String> list = new ArrayList<>();
        if (getView() != null) {
            GridLayout gridLayout = (GridLayout) getView().findViewById(R.id.gridLayout);
            for (int i = 0 ; i < gridLayout.getChildCount(); i++) {
                View parent = gridLayout.getChildAt(i);
                if (parent.findViewById(R.id.item_grid_card).getTag().equals(true)) {
                    list.add(((TextView) parent.findViewById(R.id.text_item_grid)).getText().toString());
                }
            }
        }
        return list;
    }

    @Override
    public void onClick(View v) {

        CardView cardView = (CardView) v;

        if (cardView.getTag().equals(false)) {
            cardView.setTag(true);

            TypedValue typedValue = new TypedValue();
            TypedArray a = getContext().obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorPrimaryDark});
            cardView.setCardBackgroundColor(a.getColor(0, 0));
            a.recycle();
        } else {
            cardView.setTag(false);
            cardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            return;
        }

        cardView.setTag(true);

        // get the center for the clipping circle
        int cx = (v.getLeft() + v.getRight()) / 2;
        int cy = (v.getTop() + v.getBottom()) / 2;

        // get the final radius for the clipping circle
        int dx = Math.max(cx, v.getWidth() - cx);
        int dy = Math.max(cy, v.getHeight() - cy);
        float finalRadius = (float) Math.hypot(dx, dy);

        if (mQuizzQuestion != null && getView() != null) {
            GridLayout gridLayout = (GridLayout) getView().findViewById(R.id.gridLayout);
            for (int i = 0; i < gridLayout.getChildCount(); i++) {
                View parent = gridLayout.getChildAt(i);
                final CardView card = (CardView) parent.findViewById(R.id.item_grid_card);
                if (card.getTag().equals(true) && card != cardView && !mQuizzQuestion.isMultipleChoise()) {
                    card.setTag(false);
                    card.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                }
            }
        }

        mAnimator = ViewAnimationUtils.createCircularReveal(v, cx, cy, 0, finalRadius);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.setDuration(500);
        mAnimator.start();
    }
}
