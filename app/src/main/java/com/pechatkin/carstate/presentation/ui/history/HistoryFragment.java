package com.pechatkin.carstate.presentation.ui.history;

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

import com.pechatkin.carstate.R;
import com.pechatkin.carstate.data.db.entity.Purchase;
import com.pechatkin.carstate.presentation.PurchasesViewModel;
import com.pechatkin.carstate.presentation.ui.purchases.AddOrUpdatePurchaseFragment;

public class HistoryFragment extends Fragment {

    private static final String TOAST_DELETE_ALL = "Все карточки удалены";
    private static final String TOAST_DELETE = "Запись удалена";
    private static final int DRAG_DIRS = 0;
    private static final String FRAGMENT_DIALOG = "fragment_dialog_history";
    private static final String UPDATED_PURCHASE = "UPDATED_PURCHASE";

    private PurchasesViewModel mPurchasesViewModel;
    private RecyclerView mRecyclerView;
    private HistoryAdapter mHistoryAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecyclerView(view);
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
                ItemTouchHelper.LEFT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                deletePurchase(viewHolder);
            }

            private void deletePurchase(@NonNull RecyclerView.ViewHolder viewHolder) {
                mPurchasesViewModel.delete(mHistoryAdapter.getPurchaseAt(
                        viewHolder.getAdapterPosition()));

                Toast.makeText(getActivity(), TOAST_DELETE, Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(mRecyclerView);

    }

    private void addOptionsMenu() {
        setHasOptionsMenu(true);
    }

    private void createBundleForDialogFragment(Purchase purchase) {
        DialogFragment updatePurchaseFragment = new AddOrUpdatePurchaseFragment();
        Bundle mBundle = new Bundle();
        mBundle.putParcelable(UPDATED_PURCHASE, purchase);
        updatePurchaseFragment.setArguments(mBundle);
        if(getFragmentManager() != null) {
            updatePurchaseFragment.show(getFragmentManager(), FRAGMENT_DIALOG);
        }
    }

    private void initRecyclerView(View root) {
        mRecyclerView = root.findViewById(R.id.recyler_view_history);
        mHistoryAdapter = new HistoryAdapter();
        mHistoryAdapter.setOnItemClickListener(this::createBundleForDialogFragment);
        mRecyclerView.setAdapter(mHistoryAdapter);
    }

    private void provideViewModel() {
        if( getActivity() != null) {
            mPurchasesViewModel = ViewModelProviders.of(getActivity())
                    .get(PurchasesViewModel.class);
            mPurchasesViewModel.getAllPurchasesInHistory()
                    .observe(this, purchases -> {
                        mHistoryAdapter.setPurchases(purchases);
                        mRecyclerView.scrollToPosition(RecyclerView.SCROLLBAR_POSITION_DEFAULT);
                    });
        }
    }
}
