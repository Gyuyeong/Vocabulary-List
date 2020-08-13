package com.baudaegam.pocketvocab;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class VocabRepository {

    private VocabDao vocabDao;

    private LiveData<List<Vocab>> allVocabs;
    private LiveData<List<Vocab>> allVocabsWithCategories;

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

    public LiveData<List<Vocab>> getAllVocabsWithCategories(int categoryId) {
        allVocabsWithCategories = vocabDao.getAllVocabsWithCategories(categoryId);
        return allVocabsWithCategories;
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
