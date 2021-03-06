package com.baudaegam.pocketvocab;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert
    void insert(Category category);

    @Update
    void update(Category category);

    @Delete
    void delete(Category category);

    @Query("SELECT * FROM category_table WHERE id = :categoryId")
    int getCategoryId(int categoryId);

    @Query("SELECT * FROM category_table")
    LiveData<List<Category>> getAllCategories();

    @Query("SELECT * FROM category_table WHERE id != 1")
    LiveData<List<Category>> getAllCategoriesExceptAllVocabs();
}
