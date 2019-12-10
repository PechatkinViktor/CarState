package com.pechatkin.carstate.data.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.pechatkin.carstate.data.db.AppDatabase;
import com.pechatkin.carstate.data.db.dao.PurchaseDao;
import com.pechatkin.carstate.data.db.entity.Purchase;

import java.util.List;

public class PurchasesRepository {

    private PurchaseDao mPurchaseDao;
    private LiveData<List<Purchase>> mAllPurchases;

    public PurchasesRepository(@NonNull Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        mPurchaseDao = appDatabase.mPurchaseDao();
        mAllPurchases = mPurchaseDao.getAllPurchases();
    }

    public void insert(@NonNull Purchase purchase) {
        new InsertPurchaseAsyncTask(mPurchaseDao).execute(purchase);
    }

    public void update(@NonNull Purchase purchase) {
        new UpdatePurchaseAsyncTask(mPurchaseDao).execute(purchase);
    }

    public void  delete(@NonNull Purchase purchase) {
        new DeletePurchaseAsyncTask(mPurchaseDao).execute(purchase);
    }

    public void deleteAllPurchases() {
        new DeleteAllPurchaseAsyncTask(mPurchaseDao).execute();
    }

    public LiveData<List<Purchase>> getAllPurchases() {
        return mAllPurchases;
    }

    private static class InsertPurchaseAsyncTask extends AsyncTask<Purchase, Void, Void> {

        private PurchaseDao mPurchaseDao;

        private InsertPurchaseAsyncTask(PurchaseDao purchaseDao) {
            mPurchaseDao = purchaseDao;
        }
        @Override
        protected Void doInBackground(Purchase... purchases) {
            mPurchaseDao.insert(purchases[0]);
            return null;
        }
    }

    private static class UpdatePurchaseAsyncTask extends AsyncTask<Purchase, Void, Void> {

        private PurchaseDao mPurchaseDao;

        private UpdatePurchaseAsyncTask(PurchaseDao purchaseDao) {
            mPurchaseDao = purchaseDao;
        }
        @Override
        protected Void doInBackground(Purchase... purchases) {
            mPurchaseDao.update(purchases[0]);
            return null;
        }
    }

    private static class DeletePurchaseAsyncTask extends AsyncTask<Purchase, Void, Void> {

        private PurchaseDao mPurchaseDao;

        private DeletePurchaseAsyncTask(PurchaseDao purchaseDao) {
            mPurchaseDao = purchaseDao;
        }
        @Override
        protected Void doInBackground(Purchase... purchases) {
            mPurchaseDao.delete(purchases[0]);
            return null;
        }
    }

    private static class DeleteAllPurchaseAsyncTask extends AsyncTask<Void, Void, Void> {

        private PurchaseDao mPurchaseDao;

        private DeleteAllPurchaseAsyncTask(PurchaseDao purchaseDao) {
            mPurchaseDao = purchaseDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            mPurchaseDao.deleteAll();
            return null;
        }
    }
}
