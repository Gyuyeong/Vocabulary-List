package com.baudaegam.pocketvocab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.baudaegam.pocketvocab.SelectCategoryActivity.EXTRA_CHECKED_CATEGORY_LIST;

public class FlashCardActivity extends AppCompatActivity {
    private TextView textViewVocabAndMeaning;
    private List<Integer> categoryIdList;
    private List<Vocab> quizVocabList;
    private MutableLiveData<List<Vocab>> quizLiveVocabList;
    private VocabViewModel vocabViewModel;
    private RelativeLayout relativeLayoutFlashCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card);

        textViewVocabAndMeaning = findViewById(R.id.text_view_vocab_and_meaning);
        relativeLayoutFlashCard = findViewById(R.id.relative_layout_flash_card);
        relativeLayoutFlashCard.setClickable(true);

        Intent intent = getIntent();
        categoryIdList = intent.getIntegerArrayListExtra(EXTRA_CHECKED_CATEGORY_LIST);

        vocabViewModel = ViewModelProviders.of(this).get(VocabViewModel.class);

        quizVocabList = new ArrayList<>();
        quizLiveVocabList = new MutableLiveData<>();
        for (int id : categoryIdList) {
            vocabViewModel.getLiveAllVocabsWithCategories(id).observe(this, new Observer<List<Vocab>>() {
                @Override
                public void onChanged(List<Vocab> vocabs) {
                    for (Vocab vocab : vocabs) {
                        // ====================== I am stuck here. ============================
                        // Tried many things from google, but no good news.
                    }
                }
            });
            Log.d("TAG", "onCreate: each loop " + quizLiveVocabList.getValue());
        }
        Log.d("TAG", "onCreate: outside for loop " + quizVocabList.size());
    }

    private void addVocabToQuizList(Vocab vocab) {
        quizVocabList.add(vocab);
        quizLiveVocabList.postValue(quizVocabList);
    }

//        private void setupFlashCard(Vocab vocab) {
//        textViewVocabAndMeaning.setText(vocab.getVocab());
//        relativeLayoutFlashCard.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                textViewVocabAndMeaning.setText(vocab.getMeaning());
//                return true;
//            }
//        });
//        }
    }
