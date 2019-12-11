package com.pechatkin.carstate.presentation;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.pechatkin.carstate.data.db.entity.Purchase;
import com.pechatkin.carstate.data.db.repository.PurchasesRepository;

import java.util.ArrayList;
import java.util.List;

public class PurchasesViewModel extends AndroidViewModel {

    private PurchasesRepository mPurchasesRepository;

    private LiveData<List<Purchase>> mAllPurchasesInPlanned;
    private LiveData<List<Purchase>> mAllPurchasesInHistory;

    public PurchasesViewModel(@NonNull Application application) {
        super(application);

        mPurchasesRepository = new PurchasesRepository(application);
        LiveData<List<Purchase>> allPurchases = mPurchasesRepository.getAllPurchases();
        mAllPurchasesInPlanned = Transformations.map(allPurchases,
                this::returnOnlyInPlanned);
        mAllPurchasesInHistory = Transformations.map(allPurchases,
                this::returnOnlyInHistory);
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

    public void deleteAllPurchases() { mPurchasesRepository.deleteAllPurchases(); }


    public LiveData<List<Purchase>> getAllPurchasesInPlanned() {
        return mAllPurchasesInPlanned;
    }

    public LiveData<List<Purchase>> getAllPurchasesInHistory() {
        return mAllPurchasesInHistory;
    }

    private List<Purchase> returnOnlyInPlanned(List<Purchase> newData) {
        List<Purchase> purchasesInPlanned = new ArrayList<>();

        for (Purchase purchase : newData) {
            if(!purchase.isIsHistory()) {
                purchasesInPlanned.add(purchase);
            }
        }
        return purchasesInPlanned;
    }

    private List<Purchase> returnOnlyInHistory(List<Purchase> newData) {
        List<Purchase> purchasesInHistory = new ArrayList<>();

        for (Purchase purchase : newData) {
            if(purchase.isIsHistory()) {
                purchasesInHistory.add(purchase);
            }
        }
        return purchasesInHistory;
    }
}

