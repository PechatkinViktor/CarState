package com.pechatkin.carstate.presentation;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.pechatkin.carstate.data.db.entity.Purchase;
import com.pechatkin.carstate.data.db.repository.PurchasesRepository;

import java.util.List;

public class PurchasesViewModel extends AndroidViewModel {

    private PurchasesRepository mPurchasesRepository;
    private LiveData<List<Purchase>> mAllPurchases;

    public PurchasesViewModel(@NonNull Application application) {
        super(application);

        mPurchasesRepository = new PurchasesRepository(application);
        mAllPurchases = mPurchasesRepository.getAllPurchases();
    }

    public void insert(Purchase purchase) {
        mPurchasesRepository.insert(purchase);
    }

    public void update(Purchase purchase) {
        mPurchasesRepository.update(purchase);
    }

    public void delete(Purchase purchase) {
        mPurchasesRepository.delete(purchase);
    }

    public void deleteAllPurchases() {
        mPurchasesRepository.deleteAllPurchases();
    }

    public LiveData<List<Purchase>> getAllPurchases() {
        return mAllPurchases;
    }
}
