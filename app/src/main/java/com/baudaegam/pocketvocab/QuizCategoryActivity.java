package com.baudaegam.pocketvocab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class QuizCategoryActivity extends AppCompatActivity {
    private Button buttonFlashCard;
    private Button buttonMultipleChoice;
    private VocabViewModel vocabViewModel;
    private List<Vocab> quizVocabList;
    ArrayList<Integer> checkedCategoriesId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_category);

        buttonFlashCard = findViewById(R.id.button_flash_card);
        buttonMultipleChoice = findViewById(R.id.button_multiple_choice);

        Intent intent = getIntent();
        checkedCategoriesId = intent.getIntegerArrayListExtra(SelectCategoryActivity.EXTRA_CHECKED_CATEGORY_LIST);

        vocabViewModel = ViewModelProviders.of(this).get(VocabViewModel.class);

        for (int id : checkedCategoriesId) {
            // I want to retrieve individual items from live data and put the individual items
            // in a separate list called quizVocabList.
            vocabViewModel.getLiveAllVocabsWithCategories(id).observe(this, new Observer<List<Vocab>>() {
                @Override
                public void onChanged(List<Vocab> vocabs) {

                }
            });
        }

        buttonFlashCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFlashCardActivity();
            }
        });

        buttonMultipleChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMultipleChoiceActivity();
                Log.d(null, "onClick: " + checkedCategoriesId.size());
            }
        });
    }

    public void openFlashCardActivity() {
        Intent intent = new Intent(this, FlashCardActivity.class);
        intent.putExtra(SelectCategoryActivity.EXTRA_CHECKED_CATEGORY_LIST, checkedCategoriesId);
        startActivity(intent);
    }

    public void openMultipleChoiceActivity() {
        Intent intent = new Intent(this, MultipleChoiceActivity.class);
        intent.putExtra(SelectCategoryActivity.EXTRA_CHECKED_CATEGORY_LIST, checkedCategoriesId);
        startActivity(intent);
    }

}
