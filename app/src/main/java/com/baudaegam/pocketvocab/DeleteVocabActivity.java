package com.baudaegam.pocketvocab;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import java.util.List;

// =========== A separate recyclerView activity for deleting the vocabularies ======================
public class DeleteVocabActivity extends AppCompatActivity {

    private int categoryId;

    private VocabViewModel vocabViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_vocab);

        Intent intent = getIntent();
        categoryId = intent.getIntExtra(CategoryActivity.EXTRA_CATEGORY_ID, 1);

        setTitle("Select to Delete");

        RecyclerView recyclerView = findViewById(R.id.recycler_view_delete_vocab);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final DeleteVocabAdapter adapter = new DeleteVocabAdapter();
        recyclerView.setAdapter(adapter);

        vocabViewModel = ViewModelProviders.of(this).get(VocabViewModel.class);
        vocabViewModel.getAllVocabsWithCategories(categoryId).observe(this, new Observer<List<Vocab>>() {
            @Override
            public void onChanged(List<Vocab> vocabs) {
                adapter.setVocabs(vocabs);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.delete_vocab_menu, menu);
        return true;
    }

    // ================================ What should I do here? =====================================
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_vocab:
                // ====================  Need to replace this ======================================
                Toast.makeText(this, "Delete Button Clicked", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
