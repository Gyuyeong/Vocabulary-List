package com.baudaegam.pocketvocab;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class VocabViewModel extends AndroidViewModel {
    private VocabRepository vocabRepository;
    private LiveData<List<Vocab>> allVocabs;
    private LiveData<List<Vocab>> allVocabsWithCategories;

    public VocabViewModel(@NonNull Application application) {
        super(application);
        vocabRepository = new VocabRepository(application);
        allVocabs = vocabRepository.getAllVocabs();
    }

    public void insert(Vocab vocab) {
        vocabRepository.insert(vocab);
    }

    public void update(Vocab vocab) {
        vocabRepository.update(vocab);
    }

    public void delete(Vocab vocab) {
        vocabRepository.delete(vocab);
    }

    public LiveData<List<Vocab>> getAllVocabs() {
        return allVocabs;
    }

    public LiveData<List<Vocab>> getAllVocabsWithCategories(int categoryId) {
        allVocabsWithCategories = vocabRepository.getAllVocabsWithCategories(categoryId);
        return allVocabsWithCategories;
    }
}
