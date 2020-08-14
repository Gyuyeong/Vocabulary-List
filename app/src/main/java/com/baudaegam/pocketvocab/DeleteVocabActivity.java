package com.baudaegam.pocketvocab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
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

    final DeleteVocabAdapter adapter = new DeleteVocabAdapter();

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

        recyclerView.setAdapter(adapter);

        vocabViewModel = ViewModelProviders.of(this).get(VocabViewModel.class);
        vocabViewModel.getLiveAllVocabsWithCategories(categoryId).observe(this, new Observer<List<Vocab>>() {
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
                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteVocabActivity.this);

                builder.setTitle("Are you sure you want to delete them?");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (Vocab vocab : adapter.checkedVocabs) {
                            vocabViewModel.delete(vocab);
                        }
                        Toast.makeText(DeleteVocabActivity.this, "Vocabulary Deleted", Toast.LENGTH_SHORT).show();
                        killActivity();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DeleteVocabActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void killActivity() {
        finish();
    }
}
