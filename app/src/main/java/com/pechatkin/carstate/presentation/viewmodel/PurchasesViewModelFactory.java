package com.pechatkin.carstate.presentation.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.pechatkin.carstate.data.repository.PurchasesRepository;

public class PurchasesViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Context mApplicationContext;

    public PurchasesViewModelFactory(@NonNull Context context) {
        mApplicationContext = context.getApplicationContext();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (PurchasesViewModel.class.equals(modelClass)) {
            PurchasesRepository repository = new PurchasesRepository(mApplicationContext);
            return (T) new PurchasesViewModel(repository);
        }else {
            return super.create(modelClass);
        }
    }
}
