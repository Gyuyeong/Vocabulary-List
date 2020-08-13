package com.baudaegam.pocketvocab;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "vocab_table")
public class Vocab {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String vocab;

    private String meaning;

    private String notes;

    private int count;

    @ForeignKey(
            entity = Category.class,
            parentColumns = "id",
            childColumns = "categoryId",
            onDelete = CASCADE
    )
    private int categoryId;

    public Vocab(String vocab, String meaning, String notes, int count, int categoryId) {
        this.vocab = vocab;
        this.meaning = meaning;
        this.notes = notes;
        this.count = count;
        this.categoryId = categoryId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getVocab() {
        return vocab;
    }

    public String getMeaning() {
        return meaning;
    }

    public String getNotes() {
        return notes;
    }

    public int getCount() {
        return count;
    }

    public int getCategoryId() {
        return categoryId;
    }
}
