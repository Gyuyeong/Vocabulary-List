package com.baudaegam.pocketvocab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SelectCategoryActivity extends AppCompatActivity {
    private CategoryViewModel categoryViewModel;
    final SelectCategoryAdapter adapter = new SelectCategoryAdapter();

    public static final String EXTRA_CHECKED_CATEGORY_LIST =
            "com.baudaegam.pocketvocab.EXTRA_CHECKED_CATEGORY_LIST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);

        setTitle("Select Category");

        RecyclerView recyclerView = findViewById(R.id.recycler_view_select_category);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.getAllCategoriesExceptAllVocabs().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                adapter.setCategories(categories);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.select_category_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.select_category) {
            Intent intent = new Intent(SelectCategoryActivity.this, QuizCategoryActivity.class);
            ArrayList<Integer> checkedCategoryIds = new ArrayList<>();

            for (Category category : adapter.checkedCategories) {
                checkedCategoryIds.add(category.getId());
            }
            if (checkedCategoryIds.size() < 1) {
                Toast.makeText(this, "You need to select at least one category", Toast.LENGTH_SHORT).show();
            } else {
                intent.putExtra(EXTRA_CHECKED_CATEGORY_LIST, checkedCategoryIds);
                startActivity(intent);
            }

        } else if (item.getItemId() == R.id.select_all_categories) {
            if (adapter.isSelectedAll) {
                adapter.clearAll();
            } else {
                adapter.selectAll();
            }

        } else {
            return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}
