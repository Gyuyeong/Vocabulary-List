package com.baudaegam.pocketvocab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class DeleteCategoryActivity extends AppCompatActivity {
    public static final int ALL_VOCAB_CATEGORY_ID = 1;

    private CategoryViewModel categoryViewModel;
    final DeleteCategoryAdapter adapter = new DeleteCategoryAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_category);

        setTitle("Select to Delete");

        RecyclerView recyclerView = findViewById(R.id.recycler_view_delete_category);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                adapter.setCategories(categories);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.delete_category_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_category:
                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteCategoryActivity.this);

                builder.setTitle("Are you sure you want to delete them?");
                builder.setMessage("All vocabularies in the category will be lost.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (Category category : adapter.checkedCategories) {
                            if (category.getId() == ALL_VOCAB_CATEGORY_ID) {
                                Toast.makeText(DeleteCategoryActivity.this, "Cannot Delete All Vocabulary Category", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            categoryViewModel.delete(category);
                        }
                        killActivity();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DeleteCategoryActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
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
