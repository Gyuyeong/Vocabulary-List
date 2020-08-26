package com.baudaegam.pocketvocab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import static com.baudaegam.pocketvocab.SelectCategoryActivity.EXTRA_CHECKED_CATEGORY_LIST;

public class FlashCardActivity extends AppCompatActivity {
    private TextView textViewVocabAndMeaning;
    private List<Integer> categoryIdList;
    private List<Vocab> quizVocabList;
    private VocabViewModel vocabViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card);

        textViewVocabAndMeaning = findViewById(R.id.text_view_vocab_and_meaning);

        Intent intent = getIntent();
        categoryIdList = intent.getIntegerArrayListExtra(EXTRA_CHECKED_CATEGORY_LIST);

        vocabViewModel = ViewModelProviders.of(this).get(VocabViewModel.class);

        for (int id : categoryIdList) {
            quizVocabList.addAll(vocabViewModel.getAllVocabsWithCategories(id));
            }
        Log.d("TAG", "onCreate: " + quizVocabList.size());
        }
    }
