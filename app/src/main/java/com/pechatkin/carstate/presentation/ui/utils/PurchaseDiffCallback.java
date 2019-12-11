package com.pechatkin.carstate.presentation.ui.utils;

import androidx.recyclerview.widget.DiffUtil;

import com.pechatkin.carstate.data.db.entity.Purchase;

import java.util.List;

public class PurchaseDiffCallback extends DiffUtil.Callback {

    private List<Purchase> mOldPurchase;
    private List<Purchase> mNewPurchase;

    public PurchaseDiffCallback(List<Purchase> oldPurchase, List<Purchase> newPurchase) {
        mOldPurchase = oldPurchase;
        mNewPurchase = newPurchase;
    }

    @Override
    public int getOldListSize() {
        return mOldPurchase.size();
    }

    @Override
    public int getNewListSize() {
        return mNewPurchase.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldPurchase.get(oldItemPosition)
                .getId() == mNewPurchase.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldPurchase.get(oldItemPosition).equals(mNewPurchase.get(newItemPosition));
    }
}
