package com.baudaegam.pocketvocab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

// ================================ Title Screen =================================================
public class MainActivity extends AppCompatActivity {
    private Button categoryButton;
    private Button searchVocabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoryButton = findViewById(R.id.category);
        searchVocabButton = findViewById(R.id.search);

        categoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCategoryActivity();
            }
        });

        searchVocabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchVocabActivity();
            }
        });
    }

    public void openCategoryActivity() {
        Intent intent = new Intent(this, CategoryActivity.class);
        startActivity(intent);
    }

    public void openSearchVocabActivity() {
        Intent intent = new Intent(this, SearchVocabActivity.class);
        startActivity(intent);
    }

}
