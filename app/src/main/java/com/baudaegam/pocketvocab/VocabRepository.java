package com.baudaegam.pocketvocab;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class VocabRepository {

    private VocabDao vocabDao;

    private LiveData<List<Vocab>> allVocabs;
    private LiveData<List<Vocab>> allVocabsWithCategories;
    private LiveData<List<Vocab>> allSearchedVocabs;

    private final Executor ioExecutor = Executors.newSingleThreadExecutor();

    public VocabRepository(Application application) {
        Database database = Database.getInstance(application);
        vocabDao = database.vocabDao();

        allVocabs = vocabDao.getAllVocabs();
    }

    public void insert(Vocab vocab) {
        new VocabRepository.InsertVocabAsyncTask(vocabDao).execute(vocab);
    }

    public void update(Vocab vocab) {
        new VocabRepository.UpdateVocabAsyncTask(vocabDao).execute(vocab);
    }

    public void delete(Vocab vocab) {
        new VocabRepository.DeleteVocabAsyncTask(vocabDao).execute(vocab);
    }

    public LiveData<List<Vocab>> getAllVocabs() {
        return allVocabs;
    }

    public List<Vocab> getAllVocabsWithCategories(int categoryId) {
        return vocabDao.getAllVocabsWithCategories(categoryId);
    }

    public LiveData<List<Vocab>> getLiveAllVocabsWithCategories(int categoryId) {
        allVocabsWithCategories = vocabDao.getLiveAllVocabsWithCategories(categoryId);
        return allVocabsWithCategories;
    }

    public LiveData<List<Vocab>> getAllSearchedVocabs(String vocab) {
        allSearchedVocabs = vocabDao.getSearchedVocabs(vocab);
        return allSearchedVocabs;
    }

    public String getCategoryName(int categoryId) {
        return vocabDao.getCategoryName(categoryId);
    }

    private static class InsertVocabAsyncTask extends AsyncTask<Vocab, Void, Void> {
        private VocabDao vocabDao;

        private InsertVocabAsyncTask(VocabDao vocabDao) {
            this.vocabDao = vocabDao;
        }

        @Override
        protected Void doInBackground(Vocab... vocabs) {
            vocabDao.insert(vocabs[0]);
            return null;
        }
    }

    private static class UpdateVocabAsyncTask extends AsyncTask<Vocab, Void, Void> {
        private VocabDao vocabDao;

        private UpdateVocabAsyncTask(VocabDao vocabDao) {
            this.vocabDao = vocabDao;
        }

        @Override
        protected Void doInBackground(Vocab... vocabs) {
            vocabDao.update(vocabs[0]);
            return null;
        }
    }

    private static class DeleteVocabAsyncTask extends AsyncTask<Vocab, Void, Void> {
        private VocabDao vocabDao;

        private DeleteVocabAsyncTask(VocabDao vocabDao) {
            this.vocabDao = vocabDao;
        }

        @Override
        protected Void doInBackground(Vocab... vocabs) {
            vocabDao.delete(vocabs[0]);
            return null;
        }
    }

}
