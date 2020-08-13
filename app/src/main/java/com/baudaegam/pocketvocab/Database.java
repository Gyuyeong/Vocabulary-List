package com.baudaegam.pocketvocab;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@androidx.room.Database(entities = {Category.class, Vocab.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    private static Database instance;

    abstract public CategoryDao categoryDao();

    abstract public VocabDao vocabDao();

    public static synchronized Database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    Database.class, "main_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private CategoryDao categoryDao;

        private PopulateDbAsyncTask(Database db) {
            categoryDao = db.categoryDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            categoryDao.insert(new Category("base"));
            return null;
        }
    }
}
