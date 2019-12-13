package com.pechatkin.carstate.presentation.ui.purchases;

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
import com.pechatkin.carstate.presentation.viewmodel.PurchasesViewModel;
import com.pechatkin.carstate.presentation.viewmodel.PurchasesViewModelFactory;

import static com.pechatkin.carstate.presentation.ui.utils.Const.STATE_IS_PLANNED;
import static com.pechatkin.carstate.presentation.ui.utils.Const.UPDATED_PURCHASE;

public class AddOrUpdatePurchaseFragment extends DialogFragment {

    private PurchasesViewModel mPurchasesViewModel;
    private Bundle mBundle;
    private EditText mEditTextTitle;
    private EditText mEditTextDesc;
    private EditText mEditTextPrise;
    private Spinner mSpinnerCategory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_add_or_update, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        getInputValues(view);
        setupMvvm();
    }

    private void setupMvvm() {
        if( getActivity() != null) {
            mPurchasesViewModel = ViewModelProviders
                    .of(getActivity(), new PurchasesViewModelFactory(getActivity()))
                    .get(PurchasesViewModel.class);
        }
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
        ((TextView)view.findViewById(R.id.text_title_add_purchase)).setText(R.string.update_card);
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
        Button mButtonOk = root.findViewById(R.id.button_add_purchase);

        mButtonOk.setOnClickListener(view -> {

            if (mBundle != null) {
                Purchase mUpdatedPurchase = mBundle.getParcelable(UPDATED_PURCHASE);
                if(mUpdatedPurchase != null) {
                    mPurchasesViewModel.createUpdatedPurchase(
                            mUpdatedPurchase,
                            mEditTextTitle.getText().toString(),
                            mEditTextDesc.getText().toString(),
                            Float.valueOf(mEditTextPrise.getText().toString()),
                            mSpinnerCategory.getSelectedItem().toString(),
                            STATE_IS_PLANNED);
                }
            } else {
                mPurchasesViewModel.createNewPurchase(
                        mEditTextTitle.getText().toString(),
                        mEditTextDesc.getText().toString(),
                        Float.valueOf(mEditTextPrise.getText().toString()),
                        mSpinnerCategory.getSelectedItem().toString());
            }
            AddOrUpdatePurchaseFragment.this.dismiss();
        });
    }
}
