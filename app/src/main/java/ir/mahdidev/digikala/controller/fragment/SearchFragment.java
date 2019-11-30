package ir.mahdidev.digikala.controller.fragment;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.controller.activity.MainActivity;
import ir.mahdidev.digikala.controller.activity.ProductsListActivity;
import ir.mahdidev.digikala.eventbus.ListProductData;
import ir.mahdidev.digikala.util.Const;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    private static final int REQ_CODE_SPEECH_INPUT = 110;

    @OnClick(R.id.back_search)
    void onBackClicked(){
        getActivity().finish();
    }
    @BindView(R.id.search_edt)
    EditText searchEdt;
    @BindView(R.id.voice_to_text_img)
    ImageView voiceToText;
    @BindView(R.id.clear_edit_text)
    ImageView clearEditText;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this , view);
        searchFunction();
    }

    private void searchFunction() {
        searchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()>1){
                    voiceToText.setVisibility(View.GONE);
                    clearEditText.setVisibility(View.VISIBLE);

                }else {
                    voiceToText.setVisibility(View.VISIBLE);
                    clearEditText.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        searchEdt.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchFunction(v.getText().toString());
                return true;
            }
            return false;
        });
        clearEditText.setOnClickListener(view -> searchEdt.setText(""));
        voiceToText.setOnClickListener(view -> {
            startVoiceInput();
        });
    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fa");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, R.string.tell_name_your_product);
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == Activity.RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    searchFunction(result.get(0));
                }
                break;
            }

        }
    }
    private void searchFunction(String searchText) {
        startActivity(ProductsListActivity.newIntent
                (getActivity(),new ListProductData(searchText ,
                Const.OrderTag.MOST_VISITING_PRODUCT , "desc" , searchText)));
    }
}
