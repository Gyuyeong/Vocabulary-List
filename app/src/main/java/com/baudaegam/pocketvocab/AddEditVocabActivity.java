package com.baudaegam.pocketvocab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
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

    private int categoryId;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vocab);

        editTextVocab = findViewById(R.id.edit_text_vocab);
        editTextMeaning = findViewById(R.id.edit_text_meaning);
        editTextNotes = findViewById(R.id.edit_text_notes);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        categoryId = intent.getIntExtra(CategoryActivity.EXTRA_CATEGORY_ID, 1);
        count = intent.getIntExtra(EXTRA_COUNT, 1);

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
