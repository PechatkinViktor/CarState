package com.pechatkin.carstate.presentation.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.pechatkin.carstate.data.db.entity.Purchase;
import com.pechatkin.carstate.data.db.repository.PurchasesRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.pechatkin.carstate.presentation.ui.utils.Const.DATE_FORMAT_PATTERN;
import static com.pechatkin.carstate.presentation.ui.utils.Const.STATE_IS_HISTORY;

public class PurchasesViewModel extends ViewModel {

    private PurchasesRepository mPurchasesRepository;

    private LiveData<List<Purchase>> mAllPurchasesInPlanned;
    private LiveData<List<Purchase>> mAllPurchasesInHistory;
    private final MutableLiveData<Purchase> updatedPurchase = new MutableLiveData<>();

    PurchasesViewModel(@NonNull PurchasesRepository purchasesRepository) {

        mPurchasesRepository = purchasesRepository;
        LiveData<List<Purchase>> allPurchases = mPurchasesRepository.getAllPurchases();
        mAllPurchasesInPlanned = Transformations.map(allPurchases, this::returnOnlyInPlanned);
        mAllPurchasesInHistory = Transformations.map(allPurchases, this::returnOnlyInHistory);
    }

    public void insert(Purchase purchase) { mPurchasesRepository.insert(purchase); }

    public void update(Purchase purchase) { mPurchasesRepository.update(purchase); }

    public void delete(Purchase purchase) { mPurchasesRepository.delete(purchase); }

    public void deleteAllPurchases() { mPurchasesRepository.deleteAllPurchases(); }

    public LiveData<List<Purchase>> getAllPurchasesInPlanned() {
        return mAllPurchasesInPlanned;
    }

    public LiveData<List<Purchase>> getAllPurchasesInHistory() {
        return mAllPurchasesInHistory;
    }

    public LiveData<Purchase> getUpdatePurchase() { return updatedPurchase; }

    public void createUpdatedPurchase(Purchase oldPurchase, String newPurchaseTitle,
                                      String newPurchaseDesc, float newPurchasePrise,
                                      String newPurchaseCategory, boolean isHistory) {
        String mCurrentDate = new SimpleDateFormat(
                DATE_FORMAT_PATTERN, Locale.US).format(new Date());
        oldPurchase.setTitle(newPurchaseTitle);
        oldPurchase.setDescription(newPurchaseDesc);
        oldPurchase.setPrise(newPurchasePrise);
        oldPurchase.setCategory(newPurchaseCategory);
            if(isHistory){
                oldPurchase.setAddHistoryDate(mCurrentDate);
                oldPurchase.setIsHistory(STATE_IS_HISTORY);
            }

            updatedPurchase.setValue(oldPurchase);
            updatedPurchase.setValue(oldPurchase);
    }

    public void createNewPurchase(String newPurchaseTitle, String newPurchaseDesc,
                                  float newPurchasePrise, String newPurchaseCategory) {
        String mCurrentDate = new SimpleDateFormat(
                DATE_FORMAT_PATTERN, Locale.US).format(new Date());
        Purchase newPurchase = new Purchase(newPurchaseTitle, newPurchaseDesc,
                mCurrentDate, newPurchasePrise, newPurchaseCategory);
        this.insert(newPurchase);
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

