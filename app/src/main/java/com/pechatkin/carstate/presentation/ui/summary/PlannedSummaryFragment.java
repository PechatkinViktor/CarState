package com.pechatkin.carstate.presentation.ui.summary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.pechatkin.carstate.R;
import com.pechatkin.carstate.presentation.viewmodel.PurchasesViewModel;
import com.pechatkin.carstate.presentation.viewmodel.PurchasesViewModelFactory;

public class PlannedSummaryFragment extends Fragment{

    private PurchasesViewModel mPurchasesViewModel;

    private TextView mPrise;
    private TextView mAutoParts;
    private TextView mConsum;
    private TextView mTools;
    private TextView mAccessories;
    private TextView mDiscTires;
    private TextView mPOther;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_summary_planned, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setupMvvm();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void initViews(@NonNull View view) {
        mPrise = view.findViewById(R.id.summary_planned_prise);
        mAutoParts = view.findViewById(R.id.summary_planned_auto_parts);
        mConsum = view.findViewById(R.id.summary_planned_consum);
        mTools = view.findViewById(R.id.summary_planned_tools);
        mAccessories = view.findViewById(R.id.summary_planned_accessories);
        mDiscTires = view.findViewById(R.id.summary_planned_disc_tires);
        mPOther = view.findViewById(R.id.summary_planned_other);
    }

    private void setupMvvm() {
        if( getActivity() != null) {
            mPurchasesViewModel = ViewModelProviders
                    .of(getActivity(), new PurchasesViewModelFactory(getActivity()))
                    .get(PurchasesViewModel.class);
        }
        mPurchasesViewModel.getAllPurchasesInPlanned().observe(this, purchases -> {
            mPrise.setText(mPurchasesViewModel.getAllSumm(purchases));
            mAutoParts.setText(mPurchasesViewModel.getSummByCategory(
                    purchases, getString(R.string.auto_parts)));
            mConsum.setText(mPurchasesViewModel.getSummByCategory(
                    purchases, getString(R.string.consum)));
            mTools.setText(mPurchasesViewModel.getSummByCategory(
                    purchases, getString(R.string.tools)));
            mAccessories.setText(mPurchasesViewModel.getSummByCategory(
                    purchases, getString(R.string.accessories)));
            mDiscTires.setText(mPurchasesViewModel.getSummByCategory(
                    purchases, getString(R.string.disc_tires)));
            mPOther.setText(mPurchasesViewModel.getSummByCategory(
                    purchases, getString(R.string.other)));
        });
    }
}
