package com.pechatkin.carstate.presentation.ui.purchases;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.pechatkin.carstate.R;
import com.pechatkin.carstate.data.db.entity.Purchase;
import com.pechatkin.carstate.presentation.PurchasesViewModel;

import java.util.Date;

public class AddPurchaseFragment extends DialogFragment {

    private static final String DATE_FORMAT_PATTERN = "dd.MM.yyyy";

    private EditText mEditTestTitle;
    private EditText mEditTestDesc;
    private EditText mEditTestPrise;
    private Spinner mSpinnerCategory;
    private Button mButtonAdd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_purchase, container, false);

        initViews(root);
        return root;
    }

    private void initViews(View root) {
        mEditTestTitle = root.findViewById(R.id.text_input_title_add_purchase);
        mEditTestDesc = root.findViewById(R.id.text_input_desc_add_purchase);
        mEditTestPrise = root.findViewById(R.id.text_input_prise_add_purchase);
        mSpinnerCategory = root.findViewById(R.id.spinner_category_add_purchase);
        mButtonAdd = root.findViewById(R.id.button_add_purchase);

        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewPurchase();
            }

            private void createNewPurchase() {
                String newPurchaseTitle =
                        String.valueOf(mEditTestTitle.getText());
                String newPurchaseDesc =
                        String.valueOf(mEditTestDesc.getText());
                float newPurchasePrise =
                        Float.valueOf(String.valueOf(mEditTestPrise.getText()));
                String newPurchaseDateAdded =
                        new SimpleDateFormat(DATE_FORMAT_PATTERN).format(new Date());
                String newPurchaseCategory =
                        String.valueOf(mSpinnerCategory.getSelectedItem());

                Purchase newPurchase = new Purchase(newPurchaseTitle, newPurchaseDesc,
                        newPurchaseDateAdded, newPurchasePrise, newPurchaseCategory);

                if( getActivity() != null) {
                    PurchasesViewModel mPurchasesViewModel =
                            ViewModelProviders.of(getActivity()).get(PurchasesViewModel.class);
                    mPurchasesViewModel.insert(newPurchase);
                    AddPurchaseFragment.this.dismiss();
                }
            }
        });
    }
}
