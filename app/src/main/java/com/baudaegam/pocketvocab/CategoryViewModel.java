package com.baudaegam.pocketvocab;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    private CategoryRepository categoryRepository;
    private int categoryId;
    private LiveData<List<Category>> allCategories;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        categoryRepository = new CategoryRepository(application);
        allCategories = categoryRepository.getAllCategories();
    }

    // ================category=======================
    public void insert(Category category) {
        categoryRepository.insert(category);
    }

    public void update(Category category) {
        categoryRepository.update(category);
    }

    public void delete(Category category) {
        categoryRepository.delete(category);
    }

    public int getCategoryId(int categoryId) {
        categoryId = categoryRepository.getCategoryId(categoryId);
        return categoryId;
    }

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }
}
