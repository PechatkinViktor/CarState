package com.pechatkin.carstate.presentation.ui.history;

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
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.pechatkin.carstate.R;
import com.pechatkin.carstate.data.db.entity.Purchase;
import com.pechatkin.carstate.presentation.viewmodel.PurchasesViewModel;
import com.pechatkin.carstate.presentation.viewmodel.PurhaseViewModelFactory;

import java.util.Date;

import static com.pechatkin.carstate.presentation.ui.utils.Const.CARD_DELETE;
import static com.pechatkin.carstate.presentation.ui.utils.Const.CARD_SEND_TO_HISTORY;
import static com.pechatkin.carstate.presentation.ui.utils.Const.DATE_FORMAT_PATTERN;
import static com.pechatkin.carstate.presentation.ui.utils.Const.DRAG_DIRS;
import static com.pechatkin.carstate.presentation.ui.utils.Const.FRAGMENT_DIALOG_HISTORY;
import static com.pechatkin.carstate.presentation.ui.utils.Const.STATE_IS_HISTORY;
import static com.pechatkin.carstate.presentation.ui.utils.Const.TOAST_DELETE_ALL;
import static com.pechatkin.carstate.presentation.ui.utils.Const.UNDO_TEXT;
import static com.pechatkin.carstate.presentation.ui.utils.Const.UPDATED_PURCHASE;

public class HistoryFragment extends Fragment {

    private PurchasesViewModel mPurchasesViewModel;
    private RecyclerView mRecyclerView;
    private HistoryAdapter mHistoryAdapter;
    private CoordinatorLayout mLayout;

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
                switch (direction) {
                    case ItemTouchHelper.LEFT: {
                        deletePurchase(viewHolder);
                        break;
                    }
                    case ItemTouchHelper.RIGHT: {
                        sendPurchaseToPlanned(viewHolder);
                        break;
                    }
                }
            }

            private void sendPurchaseToPlanned(RecyclerView.ViewHolder viewHolder) {
                Purchase updatedPurchase = mHistoryAdapter.getPurchaseAt(
                        viewHolder.getAdapterPosition());
                updatedPurchase.setAddHistoryDate(new SimpleDateFormat(DATE_FORMAT_PATTERN)
                        .format(new Date()));
                updatedPurchase.setIsHistory(!STATE_IS_HISTORY);
                mPurchasesViewModel.update(updatedPurchase);

                undoSendToHistorySwipe(updatedPurchase);
            }

            private void deletePurchase(@NonNull RecyclerView.ViewHolder viewHolder) {
                Purchase mUndPurchase = mHistoryAdapter.getPurchaseAt(
                        viewHolder.getAdapterPosition());
                mPurchasesViewModel.delete(mHistoryAdapter.getPurchaseAt(
                        viewHolder.getAdapterPosition()));

                undoDeleteSwipe(mUndPurchase);
            }
        }).attachToRecyclerView(mRecyclerView);

    }

    private void undoSendToHistorySwipe(Purchase updatedPurchase) {
        Snackbar mUndoSnackbar = Snackbar
                .make(mLayout, CARD_SEND_TO_HISTORY, Snackbar.LENGTH_LONG)
                .setAction(UNDO_TEXT, view -> {
                    updatedPurchase.setIsHistory(STATE_IS_HISTORY);
                    mPurchasesViewModel.update(updatedPurchase);
                    mHistoryAdapter.addPurchase(updatedPurchase);
                });
        mUndoSnackbar.show();
    }

    private void undoDeleteSwipe(Purchase mUndPurchase) {
        Snackbar mUndoSnackbar = Snackbar
                .make(mLayout, CARD_DELETE, Snackbar.LENGTH_LONG)
                .setAction(UNDO_TEXT, view ->
                        mPurchasesViewModel.insert(mUndPurchase));
        mUndoSnackbar.show();
    }

    private void addOptionsMenu() {
        setHasOptionsMenu(true);
    }

    private void createBundleForDialogFragment(Purchase purchase) {
        DialogFragment updateHistoryFragment = new UpgradeHistoryFragment();
        Bundle mBundle = new Bundle();
        mBundle.putParcelable(UPDATED_PURCHASE, purchase);
        updateHistoryFragment.setArguments(mBundle);
        if(getFragmentManager() != null) {
            updateHistoryFragment.show(getFragmentManager(), FRAGMENT_DIALOG_HISTORY);
        }
    }

    private void initRecyclerView(View root) {
        mRecyclerView = root.findViewById(R.id.recyler_view_history);
        mHistoryAdapter = new HistoryAdapter();
        mHistoryAdapter.setOnItemClickListener(this::createBundleForDialogFragment);
        mRecyclerView.setAdapter(mHistoryAdapter);
        mLayout = root.findViewById(R.id.history_layout);
    }

    private void provideViewModel() {
        if( getActivity() != null) {
            mPurchasesViewModel = ViewModelProviders.of(getActivity(), new PurhaseViewModelFactory(getActivity()))
                    .get(PurchasesViewModel.class);
            mPurchasesViewModel.getAllPurchasesInHistory()
                    .observe(this, purchases -> {
                        mHistoryAdapter.setPurchases(purchases);
                        mRecyclerView.scrollToPosition(RecyclerView.SCROLLBAR_POSITION_DEFAULT);
                    });
        }
    }
}
