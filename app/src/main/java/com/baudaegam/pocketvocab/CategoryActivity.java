package com.baudaegam.pocketvocab;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


// ==================== Activity to display the categories of words ===================
public class CategoryActivity extends AppCompatActivity {
    private CategoryViewModel categoryViewModel;
    public static final String EXTRA_CATEGORY_ID =
            "com.baudaegam.pocketvocab.EXTRA_CATEGORY_ID";
    public static final String EXTRA_CATEGORY =
            "com.baudaegam.pocketvocab.EXTRA_CATEGORY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_category);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final CategoryAdapter adapter = new CategoryAdapter();
        recyclerView.setAdapter(adapter);
// ==================== click to access vocabularies of the categories =============================
        adapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Category category) {
                Intent intent = new Intent(CategoryActivity.this, VocabActivity.class);
                intent.putExtra(EXTRA_CATEGORY_ID, category.getId());
                intent.putExtra(EXTRA_CATEGORY, category.getCategory());
                startActivity(intent);
            }
        });


        // =============================Adding Category with Alert dialog ==========================
        FloatingActionButton buttonAddCategory = findViewById(R.id.button_add_category);
        buttonAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(CategoryActivity.this);
                builder.setTitle("Enter New Category");
                builder.setCancelable(false);

                final EditText editTextCategory = new EditText(CategoryActivity.this);
                builder.setView(editTextCategory);


                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(CategoryActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                final AlertDialog dialog = builder.create();
                dialog.show();

                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Boolean wantToCloseDialog = false;
                        String categoryName = editTextCategory.getText().toString();
                        if (categoryName.trim().isEmpty()) {
                            Toast.makeText(CategoryActivity.this, "Please add Name", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            Category newCategory = new Category(categoryName);
                            categoryViewModel.insert(newCategory);
                            wantToCloseDialog = true;
                        }
                        if (wantToCloseDialog) {
                            dialog.dismiss();
                        }
                }
            });
        }

    });

        // ========================================================================================
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                adapter.setCategories(categories);
            }
        });
}
}
