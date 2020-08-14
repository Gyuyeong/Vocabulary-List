package com.baudaegam.pocketvocab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;

public class QuizCategoryActivity extends AppCompatActivity {
    private Button buttonFlashCard;
    private Button buttonMultipleChoice;
    ArrayList<Integer> checkedCategoriesId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_category);

        buttonFlashCard = findViewById(R.id.button_flash_card);
        buttonMultipleChoice = findViewById(R.id.button_multiple_choice);

        Intent intent = getIntent();
        checkedCategoriesId = intent.getIntegerArrayListExtra(SelectCategoryActivity.EXTRA_CHECKED_CATEGORY_LIST);

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
