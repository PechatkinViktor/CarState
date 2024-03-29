package com.pechatkin.carstate.presentation.viewmodel;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.pechatkin.carstate.data.db.entity.Purchase;
import com.pechatkin.carstate.data.repository.PurchasesRepository;
import com.pechatkin.carstate.data.tutorial.TutorialDataProvider;
import com.pechatkin.carstate.domain.model.ViewPagerItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.pechatkin.carstate.presentation.ui.utils.Const.DATE_FORMAT_PATTERN;
import static com.pechatkin.carstate.presentation.ui.utils.Const.PRISE_FORMAT;

public class PurchasesViewModel extends ViewModel {

    private PurchasesRepository mPurchasesRepository;
    private TutorialDataProvider mTutorialDataProvider;

    private LiveData<List<Purchase>> mAllPurchasesInPlanned;
    private LiveData<List<Purchase>> mAllPurchasesInHistory;
    private final MutableLiveData<Purchase> updatedPurchase = new MutableLiveData<>();

    PurchasesViewModel(@NonNull PurchasesRepository purchasesRepository,
                       TutorialDataProvider tutorialDataProvider) {

        mPurchasesRepository = purchasesRepository;
        mTutorialDataProvider = tutorialDataProvider;
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

    public void createUpdatedPurchase(@NonNull Purchase oldPurchase,@NonNull String newPurchaseTitle,
                                      String newPurchaseDesc, float newPurchasePrise,
                                      String newPurchaseCategory, boolean isHistory) {
        String mCurrentDate = new SimpleDateFormat(
                DATE_FORMAT_PATTERN, Locale.getDefault()).format(new Date());
        oldPurchase.setTitle(newPurchaseTitle);
        oldPurchase.setDescription(newPurchaseDesc);
        oldPurchase.setPrise(newPurchasePrise);
        oldPurchase.setCategory(newPurchaseCategory);
        oldPurchase.setAddPurchasesDate(mCurrentDate);
        oldPurchase.setAddHistoryDate(mCurrentDate);
        oldPurchase.setIsHistory(isHistory);
         updatedPurchase.setValue(oldPurchase);
    }

    public List<ViewPagerItem> getDataForTutorialAdapter() {
        return mTutorialDataProvider.getTutorialData();
    }

    public void createNewPurchase(@NonNull String newPurchaseTitle,@NonNull String newPurchaseDesc,
                                  float newPurchasePrise,@NonNull String newPurchaseCategory) {
        String mCurrentDate = new SimpleDateFormat(
                DATE_FORMAT_PATTERN, Locale.getDefault()).format(new Date());
        Purchase newPurchase = new Purchase(newPurchaseTitle, newPurchaseDesc,
                mCurrentDate, newPurchasePrise, newPurchaseCategory);
        this.insert(newPurchase);
    }

    @SuppressLint("DefaultLocale")
    public String getAllSumm(@NonNull List<Purchase> purchases) {
        float result = 0;
        for (Purchase purchase : purchases) {
            result+= purchase.getPrise();
        }
        return String.format(PRISE_FORMAT, result);
    }

    @SuppressLint("DefaultLocale")
    public String getSummByCategory(@NonNull List<Purchase> purchases,@NonNull String category) {
        float result = 0;
        for (Purchase purchase : purchases) {
            if(purchase.getCategory().equals(category)) {
                result+= purchase.getPrise();
            }
        }
        return String.format(PRISE_FORMAT, result);
    }

    private List<Purchase> returnOnlyInPlanned(@NonNull List<Purchase> newData) {
        List<Purchase> purchasesInPlanned = new ArrayList<>();

        for (Purchase purchase : newData) {
            if(!purchase.isIsHistory()) {
                purchasesInPlanned.add(purchase);
            }
        }
        return purchasesInPlanned;
    }

    private List<Purchase> returnOnlyInHistory(@NonNull List<Purchase> newData) {
        List<Purchase> purchasesInHistory = new ArrayList<>();

        for (Purchase purchase : newData) {
            if(purchase.isIsHistory()) {
                purchasesInHistory.add(purchase);
            }
        }
        return purchasesInHistory;
    }
}

