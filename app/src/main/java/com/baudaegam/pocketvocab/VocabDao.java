package com.baudaegam.pocketvocab;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface VocabDao {

    @Insert
    void insert(Vocab vocab);

    @Update
    void update(Vocab vocab);

    @Delete
    void delete(Vocab vocab);

    @Query("SELECT * FROM vocab_table ORDER BY count DESC")
    LiveData<List<Vocab>> getAllVocabs();

    @Query("SELECT * FROM vocab_table WHERE categoryId = :categoryId ORDER BY count DESC")
    LiveData<List<Vocab>> getAllVocabsWithCategories(int categoryId);
}
