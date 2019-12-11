package com.pechatkin.carstate.presentation.ui.purchases;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.pechatkin.carstate.R;
import com.pechatkin.carstate.data.db.entity.Purchase;
import com.pechatkin.carstate.presentation.PurchasesViewModel;

import java.util.Date;

public class AddOrUpdatePurchaseFragment extends DialogFragment {

    private static final String DATE_FORMAT_PATTERN = "dd.MM.yyyy";
    private static final String UPDATED_PURCHASE = "UPDATED_PURCHASE";

    private Bundle mBundle;
    private EditText mEditTextTitle;
    private EditText mEditTextDesc;
    private EditText mEditTextPrise;
    private Spinner mSpinnerCategory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_add_purchase, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        getInputValues(view);
    }

    private void getInputValues(View view) {

        mBundle = getArguments();
        if(mBundle != null) {
            Purchase inputPurchase = mBundle.getParcelable(UPDATED_PURCHASE);

            if (inputPurchase != null) {
                setDefaultTextViewValues(view, inputPurchase);
            }

        }
    }

    private void setDefaultTextViewValues(View view, Purchase inputPurchase) {
        mEditTextTitle.setText(inputPurchase.getTitle());
        mEditTextDesc.setText(inputPurchase.getDescription());
        mEditTextPrise.setText(String.valueOf(inputPurchase.getPrise()));
        findSpinnerItemPosition(inputPurchase.getCategory());
        ((Button)view.findViewById(R.id.button_add_purchase)).setText(R.string.update);
        ((TextView)view.findViewById(R.id.text_title_add_purchase)).setText(getString(R.string.update_card));
    }

    private void findSpinnerItemPosition(String mCategory) {
        for (int i=0; i<mSpinnerCategory.getCount(); i++){
            if (mSpinnerCategory.getItemAtPosition(i).toString().equalsIgnoreCase(mCategory)){
                mSpinnerCategory.setSelection(i);
            }
        }
    }

    private void initViews(View root) {
        mEditTextTitle = root.findViewById(R.id.text_input_title_add_purchase);
        mEditTextDesc = root.findViewById(R.id.text_input_desc_add_purchase);
        mEditTextPrise = root.findViewById(R.id.text_input_prise_add_purchase);
        mSpinnerCategory = root.findViewById(R.id.spinner_category_add_purchase);
        Button buttonAdd = root.findViewById(R.id.button_add_purchase);

        buttonAdd.setOnClickListener(view -> {
            String newPurchaseTitle =
                    String.valueOf(mEditTextTitle.getText());
            String newPurchaseDesc =
                    String.valueOf(mEditTextDesc.getText());
            float newPurchasePrise =
                    Float.valueOf(String.valueOf(mEditTextPrise.getText()));
            String newPurchaseDateAdded = String.valueOf(
                    new SimpleDateFormat(DATE_FORMAT_PATTERN).format(new Date()));
            String newPurchaseCategory =
                    String.valueOf(mSpinnerCategory.getSelectedItem());
            if (mBundle != null) {
                updatePurchase(newPurchaseTitle, newPurchaseDesc, newPurchasePrise,
                        newPurchaseDateAdded, newPurchaseCategory);
            } else {
                createNewPurchase(newPurchaseTitle, newPurchaseDesc, newPurchasePrise,
                        newPurchaseDateAdded, newPurchaseCategory);
            }
        });
    }

    private void updatePurchase(String newPurchaseTitle, String newPurchaseDesc,
                                float newPurchasePrise, String newPurchaseDateAdded, String newPurchaseCategory) {
        Purchase mUdpatedPurchase = mBundle.getParcelable(UPDATED_PURCHASE);
        if(mUdpatedPurchase != null) {
            mUdpatedPurchase.setTitle(newPurchaseTitle);
            mUdpatedPurchase.setDescription(newPurchaseDesc);
            mUdpatedPurchase.setPrise(newPurchasePrise);
            mUdpatedPurchase.setAddPurchasesDate(newPurchaseDateAdded);
            mUdpatedPurchase.setCategory(newPurchaseCategory);

            if( getActivity() != null) {
                PurchasesViewModel mPurchasesViewModel =
                        ViewModelProviders.of(getActivity()).get(PurchasesViewModel.class);
                mPurchasesViewModel.update(mUdpatedPurchase);
                AddOrUpdatePurchaseFragment.this.dismiss();
            }
        }
    }

    private void createNewPurchase(String newPurchaseTitle, String newPurchaseDesc,
                                   float newPurchasePrise, String newPurchaseDateAdded, String newPurchaseCategory) {

        Purchase newPurchase = new Purchase(newPurchaseTitle, newPurchaseDesc,
                newPurchaseDateAdded, newPurchasePrise, newPurchaseCategory);

        if( getActivity() != null) {
            PurchasesViewModel mPurchasesViewModel =
                    ViewModelProviders.of(getActivity()).get(PurchasesViewModel.class);
            mPurchasesViewModel.insert(newPurchase);
            AddOrUpdatePurchaseFragment.this.dismiss();
        }
    }
}
