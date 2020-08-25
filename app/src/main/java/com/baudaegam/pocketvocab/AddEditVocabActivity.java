package com.baudaegam.pocketvocab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

// ============== Activity for adding and editing individual vocabularies ======================
public class AddEditVocabActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.baudaegam.pocketvocab.EXTRA_ID";
    public static final String EXTRA_VOCAB =
            "com.baudaegam.pocketvocab.EXTRA_VOCAB";
    public static final String EXTRA_MEANING =
            "com.baudaegam.pocketvocab.EXTRA_MEANING";
    public static final String EXTRA_NOTES =
            "com.baudaegam.pocketvocab.EXTRA_NOTES";
    public static final String EXTRA_COUNT =
            "com.baudaegam.pocketvocab.EXTRA_COUNT";
    public static final String EXTRA_CATEGORY_ID =
            "com.baudaegam.pocketvocab.EXTRA_CATEGORY_ID";

    private EditText editTextVocab;
    private EditText editTextMeaning;
    private EditText editTextNotes;
    private Spinner spinnerCategory;

    private int categoryId;
    private int count;

    private List<Category> categoryList;
    private List<String> categoryNameList;
    private CategoryViewModel categoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vocab);

        editTextVocab = findViewById(R.id.edit_text_vocab);
        editTextMeaning = findViewById(R.id.edit_text_meaning);
        editTextNotes = findViewById(R.id.edit_text_notes);
        spinnerCategory = findViewById(R.id.spinner_category);

        categoryList = new ArrayList<>();
        categoryNameList = new ArrayList<>();

        Intent intent = getIntent();
        categoryId = intent.getIntExtra(CategoryActivity.EXTRA_CATEGORY_ID, 1);
        count = intent.getIntExtra(EXTRA_COUNT, 1);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNameList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCategory.setAdapter(adapter);

        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                for (Category category : categories) {
                    categoryList.add(category);
                    categoryNameList.add(category.getCategory());

                    adapter.notifyDataSetChanged();
                }
            }
        });



        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String categoryName = parent.getItemAtPosition(position).toString();

                for (Category category: categoryList) {
                    if (category.getCategory().trim() == categoryName) {
                        categoryId = category.getId();
                        break;
                    } else {
                        continue;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setSelection(categoryId);
            }
        });

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Vocabulary");
            editTextVocab.setText(intent.getStringExtra(EXTRA_VOCAB));
            editTextMeaning.setText(intent.getStringExtra(EXTRA_MEANING));
            editTextNotes.setText(intent.getStringExtra(EXTRA_NOTES));
        } else {
            setTitle("Add Vocabulary");
        }
    }

    private void saveVocab() {
        String vocab = editTextVocab.getText().toString();
        String meaning = editTextMeaning.getText().toString();
        String notes = editTextNotes.getText().toString();

        if (vocab.trim().isEmpty() || meaning.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a Vocabulary and Meaning", Toast.LENGTH_SHORT);
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_VOCAB, vocab);
        data.putExtra(EXTRA_MEANING, meaning);
        data.putExtra(EXTRA_NOTES, notes);
        data.putExtra(EXTRA_COUNT, count);
        data.putExtra(CategoryActivity.EXTRA_CATEGORY_ID, categoryId);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save_vocab_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_vocab:
                saveVocab();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
