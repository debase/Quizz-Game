package etiennedebas.com.quizgame.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import etiennedebas.com.quizgame.R;

public class TextValueSelectionFragment extends Fragment implements QuizzFragment<String> {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_text_value_selection, container, false);
    }

    @Override
    public List<String> getSelections() {
        AppCompatEditText editText = (AppCompatEditText) getView().findViewById(R.id.edittext);
        return Arrays.asList(editText.getText().toString().toLowerCase().trim());
    }

}
