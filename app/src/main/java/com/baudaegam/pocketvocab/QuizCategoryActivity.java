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
    private MediatorLiveData<Vocab> liveDataMerger = new MediatorLiveData<>();
    private LiveData<List<Vocab>> tempVocabList;
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
            // How should I deal with retrieving individual data from live data?
            tempVocabList = vocabViewModel.getLiveAllVocabsWithCategories(id);
            liveDataMerger.addSource(tempVocabList, new Observer<List<Vocab>>() {
                private int count = 0;

                @Override
                public void onChanged(List<Vocab> vocabs) {
                    if (vocabs.size() < 1) {
                        liveDataMerger.removeSource(tempVocabList);
                    } else {
                        liveDataMerger.setValue(vocabs.get(count));
                        count++;
                        if (count >= vocabs.size()) {
                            liveDataMerger.removeSource(tempVocabList);
                        }
                    }
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
