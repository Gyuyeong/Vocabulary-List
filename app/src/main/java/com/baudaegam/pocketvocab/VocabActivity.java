package com.baudaegam.pocketvocab;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

// ============== displays all the vocabularies ==================================================
public class VocabActivity extends AppCompatActivity {
    public static final int ADD_VOCAB_REQUEST = 1;
    public static final int EDIT_VOCAB_REQUEST = 2;
    public static final int DELETE_VOCAB_REQUEST = 3;

    private int categoryId;
    private String category;

    private VocabViewModel vocabViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocab);

        Intent intent = getIntent();
        categoryId = intent.getIntExtra(CategoryActivity.EXTRA_CATEGORY_ID, 1);
        category = intent.getStringExtra(CategoryActivity.EXTRA_CATEGORY);

        setTitle(category);

        // ================ Add button ========================================================
        FloatingActionButton buttonAddVocab = findViewById(R.id.button_add_vocab);
        buttonAddVocab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VocabActivity.this, AddEditVocabActivity.class);
                intent.putExtra(CategoryActivity.EXTRA_CATEGORY_ID, categoryId);
                startActivityForResult(intent, ADD_VOCAB_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view_vocab);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final VocabAdapter adapter = new VocabAdapter();
        recyclerView.setAdapter(adapter);

        // =============click individual item to edit them ======================================
        adapter.setOnItemClickListener(new VocabAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Vocab vocab) {
                Intent intent = new Intent(VocabActivity.this, AddEditVocabActivity.class);
                intent.putExtra(AddEditVocabActivity.EXTRA_ID, vocab.getId());
                intent.putExtra(AddEditVocabActivity.EXTRA_VOCAB, vocab.getVocab());
                intent.putExtra(AddEditVocabActivity.EXTRA_MEANING, vocab.getMeaning());
                intent.putExtra(AddEditVocabActivity.EXTRA_NOTES, vocab.getNotes());
                intent.putExtra(AddEditVocabActivity.EXTRA_COUNT, vocab.getCount());
                startActivityForResult(intent, EDIT_VOCAB_REQUEST);
            }
        });

        vocabViewModel = ViewModelProviders.of(this).get(VocabViewModel.class);
        vocabViewModel.getAllVocabsWithCategories(categoryId).observe(this, new Observer<List<Vocab>>() {
            @Override
            public void onChanged(List<Vocab> vocabs) {
                adapter.setVocabs(vocabs);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_VOCAB_REQUEST && resultCode == RESULT_OK) {
            String vocab = data.getStringExtra(AddEditVocabActivity.EXTRA_VOCAB);
            String meaning = data.getStringExtra(AddEditVocabActivity.EXTRA_MEANING);
            String notes = data.getStringExtra(AddEditVocabActivity.EXTRA_NOTES);
            int receivedCategoryId = data.getIntExtra(CategoryActivity.EXTRA_CATEGORY_ID, 1);

            // need categoryId
            Vocab newVocab = new Vocab(vocab, meaning, notes, 1, receivedCategoryId);
            vocabViewModel.insert(newVocab);

            Toast.makeText(this, "Vocabulary Saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_VOCAB_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditVocabActivity.EXTRA_ID, -1);
            int count = data.getIntExtra(AddEditVocabActivity.EXTRA_COUNT, 1);
            int receivedCategoryId = data.getIntExtra(CategoryActivity.EXTRA_CATEGORY_ID, 1);

            if (id == -1) {
                Toast.makeText(this, "Edit Failed", Toast.LENGTH_SHORT).show();
                return;
            }

            String vocab = data.getStringExtra(AddEditVocabActivity.EXTRA_VOCAB);
            String meaning = data.getStringExtra(AddEditVocabActivity.EXTRA_MEANING);
            String notes = data.getStringExtra(AddEditVocabActivity.EXTRA_NOTES);

            Vocab newVocab = new Vocab(vocab, meaning, notes, count, receivedCategoryId);
            newVocab.setId(id);
            vocabViewModel.update(newVocab);

            Toast.makeText(this, "Vocabulary Edited", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.delete_vocab_menu, menu);
        return true;
    }
// ============ Delete button to go to the Delete Activity ======================================
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_vocab:
                Intent intent = new Intent(VocabActivity.this, DeleteVocabActivity.class);
                intent.putExtra(CategoryActivity.EXTRA_CATEGORY_ID, categoryId);
                startActivityForResult(intent, DELETE_VOCAB_REQUEST);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
