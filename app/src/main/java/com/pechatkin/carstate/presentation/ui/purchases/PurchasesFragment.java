package com.pechatkin.carstate.presentation.ui.purchases;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pechatkin.carstate.R;
import com.pechatkin.carstate.data.db.entity.Purchase;
import com.pechatkin.carstate.presentation.PurchasesViewModel;

import java.util.Date;


public class PurchasesFragment extends Fragment {

    private static final String FRAGMENT_DIALOG = "fragment_dialog";
    private static final String TOAST_DELETE = "Запись удалена";
    private static final String TOAST_TO_HISTORY = "Отправлено в историю";
    private static final int DRAG_DIRS = 0;
    private static final String DATE_FORMAT_PATTERN = "dd.MM.yyyy";
    private static final String TOAST_DELETE_ALL = "Все карточки удалены";
    private static final boolean STATE_IS_HISTORY = true;
    private static final String UPDATED_PURCHASE = "UPDATED_PURCHASE";

    private PurchasesViewModel mPurchasesViewModel;
    private RecyclerView mRecyclerView;
    private PurchasesAdapter mPurchasesAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_purchases, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecyclerView(view);
        initFab(view);
        provideViewModel();
        addSwipeListener();
        addOptionsMenu();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.up_bar_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all_notes) {
            mPurchasesViewModel.deleteAllPurchases();
            Toast.makeText(getActivity(), TOAST_DELETE_ALL, Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addSwipeListener() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(DRAG_DIRS,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                switch (direction) {
                    case ItemTouchHelper.LEFT: {
                        deletePurchase(viewHolder);
                        break;
                    }
                    case ItemTouchHelper.RIGHT: {
                        sendPurchaseToHistory(viewHolder);
                        break;
                    }
                }
            }

            private void sendPurchaseToHistory(@NonNull RecyclerView.ViewHolder viewHolder) {
                Purchase updatedPurchase = mPurchasesAdapter.getPurchaseAt(
                        viewHolder.getAdapterPosition());
                updatedPurchase.setAddHistoryDate(new SimpleDateFormat(DATE_FORMAT_PATTERN).format(new Date()));
                updatedPurchase.setIsHistory(STATE_IS_HISTORY);
                mPurchasesViewModel.update(updatedPurchase);

                Toast.makeText(getActivity(), TOAST_TO_HISTORY, Toast.LENGTH_SHORT).show();
            }

            private void deletePurchase(@NonNull RecyclerView.ViewHolder viewHolder) {
                mPurchasesViewModel.delete(mPurchasesAdapter.getPurchaseAt(
                        viewHolder.getAdapterPosition()));

                Toast.makeText(getActivity(), TOAST_DELETE, Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(mRecyclerView);

        mPurchasesAdapter.setOnItemClickListener(this::createBundleForDialogFragment);
    }

    private void createBundleForDialogFragment(Purchase purchase) {
        DialogFragment mAddPurchaseFragment = new AddOrUpdatePurchaseFragment();
        Bundle mBundle = new Bundle();
        mBundle.putParcelable(UPDATED_PURCHASE, purchase);
        mAddPurchaseFragment.setArguments(mBundle);
        if(getFragmentManager() != null) {
            mAddPurchaseFragment.show(getFragmentManager(), FRAGMENT_DIALOG);
        }
    }

    private void addOptionsMenu() {
        setHasOptionsMenu(true);
    }

    private void initFab(View root) {
        FloatingActionButton fab = root.findViewById(R.id.fab_purchases);

        fab.setOnClickListener(view -> {
            if(getFragmentManager() != null) {
                new AddOrUpdatePurchaseFragment().show(getFragmentManager(), FRAGMENT_DIALOG);
            }
        });
    }

    private void initRecyclerView(View root) {
        mRecyclerView = root.findViewById(R.id.recyler_view_purchases);
        mPurchasesAdapter = new PurchasesAdapter();
        mRecyclerView.setAdapter(mPurchasesAdapter);
    }

    private void provideViewModel() {
        if( getActivity() != null) {
            mPurchasesViewModel = ViewModelProviders.of(getActivity())
                    .get(PurchasesViewModel.class);
            mPurchasesViewModel.getAllPurchasesInPlanned()
                    .observe(this, purchases -> {
                        mPurchasesAdapter.setPurchases(purchases);
                        mRecyclerView.scrollToPosition(RecyclerView.SCROLLBAR_POSITION_DEFAULT);
                    });
        }
    }
}
