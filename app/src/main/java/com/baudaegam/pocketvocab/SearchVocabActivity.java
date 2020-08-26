package com.baudaegam.pocketvocab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.List;

import static com.baudaegam.pocketvocab.AddEditVocabActivity.EXTRA_CATEGORY_ID;
import static com.baudaegam.pocketvocab.VocabActivity.ADD_VOCAB_REQUEST;
import static com.baudaegam.pocketvocab.VocabActivity.EDIT_VOCAB_REQUEST;

public class SearchVocabActivity extends AppCompatActivity {
    public static final String EXTRA_CATEGORY_NAME =
            "com.baudaegam.pocketvocab.EXTRA_CATEGORY_NAME";
    public static final int SEARCH_ADD_VOCAB_REQUEST = 10;
    public static final String EXTRA_SEARCH_VOCAB =
            "com.baudaegam.pocketvocab.EXTRA_SEARCH_VOCAB";

    private VocabViewModel vocabViewModel;
    private SearchView searchView;
    private String searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_vocab);

        searchView = findViewById(R.id.search_view_vocab);
        searchView.setQuery("", false);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_search_vocab);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final SearchVocabAdapter adapter = new SearchVocabAdapter();
        recyclerView.setAdapter(adapter);

        vocabViewModel = ViewModelProviders.of(this).get(VocabViewModel.class);

        adapter.setOnItemClickListener(new SearchVocabAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Vocab vocab) {
                Intent intent = new Intent(SearchVocabActivity.this, AddEditVocabActivity.class);
                intent.putExtra(AddEditVocabActivity.EXTRA_ID, vocab.getId());
                intent.putExtra(AddEditVocabActivity.EXTRA_VOCAB, vocab.getVocab());
                intent.putExtra(AddEditVocabActivity.EXTRA_MEANING, vocab.getMeaning());
                intent.putExtra(AddEditVocabActivity.EXTRA_NOTES, vocab.getNotes());
                intent.putExtra(AddEditVocabActivity.EXTRA_COUNT, vocab.getCount());
                intent.putExtra(EXTRA_CATEGORY_ID, vocab.getCategoryId());
                startActivityForResult(intent, EDIT_VOCAB_REQUEST);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getSearchedVocabs(newText);
                return true;
            }

            private void getSearchedVocabs(String searchText) {
                searchQuery = searchText;
                searchText = "%" + searchText + "%";
                vocabViewModel.getSearchedVocabs(searchText).observe(SearchVocabActivity.this, new Observer<List<Vocab>>() {
                    @Override
                    public void onChanged(List<Vocab> vocabs) {
                        if (vocabs == null) {
                            return;
                        }
                        adapter.setSearchVocabs(vocabs);
                    }
                });
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_vocab_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_vocab:
                Intent intent = new Intent(SearchVocabActivity.this, AddEditVocabActivity.class);
                // Let's think about what to do with categories
                intent.putExtra(EXTRA_CATEGORY_NAME, 1);
                intent.putExtra(EXTRA_SEARCH_VOCAB, searchQuery);
                startActivityForResult(intent, SEARCH_ADD_VOCAB_REQUEST);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SEARCH_ADD_VOCAB_REQUEST && resultCode == RESULT_OK) {
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
}
