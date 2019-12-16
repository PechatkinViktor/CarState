package com.pechatkin.carstate.presentation.ui.tutorial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.pechatkin.carstate.R;
import com.pechatkin.carstate.presentation.viewmodel.PurchasesViewModel;
import com.pechatkin.carstate.presentation.viewmodel.PurchasesViewModelFactory;

public class FirstOpenTutorialFragment extends Fragment {

    private PurchasesViewModel mPurchasesViewModel;
    private TutorialAdapter mTutorialAdapter;
    private ViewPager2 mViewPager2;
    private Button mViewPagerButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_first_open_tutorial, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupMvvm();
        provideAdapter();
        initViewPager(view);
    }

    private void initViewPager(@NonNull View view) {
        mViewPager2 = view.findViewById(R.id.view_pager2);
        mViewPager2.setAdapter(mTutorialAdapter);
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if(mViewPager2.getCurrentItem() + 1 < mTutorialAdapter.getItemCount()) {
                    mViewPagerButton.setText(R.string.button_viewpager_go);
                } else {
                    mViewPagerButton.setText(R.string.button_viewpager_get_started);
                }
            }
        });
        mViewPagerButton = view.findViewById(R.id.view_pager_button);
        mViewPagerButton.setOnClickListener(view1 -> {
            if(mViewPager2.getCurrentItem() + 1 < mTutorialAdapter.getItemCount()) {
                mViewPager2.setCurrentItem(mViewPager2.getCurrentItem() + 1);
            } else {
                if(getActivity() != null) {
                    mViewPagerButton.setText(R.string.button_viewpager_get_started);
                    Navigation.findNavController(getActivity(), R.id.nav_host_fragment)
                            .navigate(R.id.action_firstOpenTutorialFragment_to_navigation_purchases);
                }
            }
        });
    }

    private void provideAdapter() {
        mTutorialAdapter = new TutorialAdapter();
        mTutorialAdapter.setPagerItems(mPurchasesViewModel.getDataForTutorialAdapter());
    }

    private void setupMvvm() {
        if (getActivity() != null) {
            mPurchasesViewModel = ViewModelProviders
                    .of(getActivity(), new PurchasesViewModelFactory(getActivity()))
                    .get(PurchasesViewModel.class);
        }
    }

}
