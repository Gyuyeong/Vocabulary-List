package com.baudaegam.pocketvocab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import java.util.List;

public class SearchVocabActivity extends AppCompatActivity {
    private VocabViewModel vocabViewModel;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_vocab);

        searchView = findViewById(R.id.search_view_vocab);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_search_vocab);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final SearchVocabAdapter adapter = new SearchVocabAdapter();
        recyclerView.setAdapter(adapter);

        vocabViewModel = ViewModelProviders.of(this).get(VocabViewModel.class);
        vocabViewModel.getAllVocabs().observe(this, new Observer<List<Vocab>>() {
            @Override
            public void onChanged(List<Vocab> vocabs) {
                adapter.setSearchVocabs(vocabs);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

    }
}
