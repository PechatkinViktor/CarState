package com.pechatkin.carstate.presentation.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.pechatkin.carstate.data.db.repository.PurchasesRepository;

public class PurhaseViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Context mApplicationContext;

    public PurhaseViewModelFactory(@NonNull Context context) {
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
