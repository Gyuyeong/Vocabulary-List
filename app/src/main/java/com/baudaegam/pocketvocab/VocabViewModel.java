package com.baudaegam.pocketvocab;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class VocabViewModel extends AndroidViewModel {
    private VocabRepository vocabRepository;
    private LiveData<List<Vocab>> allVocabs;
    private LiveData<List<Vocab>> allVocabsWithCategories;
    private Database database;

    

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

    public List<Vocab> getAllVocabsWithCategories(int categoryId) {
        return vocabRepository.getAllVocabsWithCategories(categoryId);
    }

    public LiveData<List<Vocab>> getLiveAllVocabsWithCategories(int categoryId) {
        allVocabsWithCategories = vocabRepository.getLiveAllVocabsWithCategories(categoryId);
        if (allVocabsWithCategories == null) {
            allVocabsWithCategories = database.vocabDao().getLiveAllVocabsWithCategories(categoryId);
        }

        return allVocabsWithCategories;
    }

}
