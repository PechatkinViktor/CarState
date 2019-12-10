package com.pechatkin.carstate.presentation.ui.purchases;

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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pechatkin.carstate.R;
import com.pechatkin.carstate.data.db.entity.Purchase;
import com.pechatkin.carstate.presentation.PurchasesViewModel;

import java.util.List;

public class PurchasesFragment extends Fragment {

    private static final String FRAGMENT_DIALOG = "fragment_dialog";
    private static final String TOAST_DELETE = "Запись удалена";
    private static final String TOAST_TO_HISTORY = "Отправлено в историю";
    private static final int DRAG_DIRS = 0;
    private static final String TOAST_DELETE_ALL = "Все карточки удалены";
    private static final boolean STATE_IS_HISTORY = true;

    private PurchasesViewModel mPurchasesViewModel;
    private RecyclerView mRecyclerView;
    private PurchasesAdapter mPurchasesAdapter;
    private FloatingActionButton mFab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_purchases, container, false);

        initRecyclerView(root);
        initFab(root);
        provideViewModel();
        addSwipeListener();
        addOptionsMenu();

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.up_bar_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all_notes) {
            mPurchasesViewModel.deleteAllPurchasesInPlanned();
            Toast.makeText(getActivity(), TOAST_DELETE_ALL, Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addSwipeListener() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(DRAG_DIRS, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
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

        mPurchasesAdapter.setOnItemClickListener(new PurchasesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Purchase purchase) {
                Toast.makeText(getActivity(), "Click for Update", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addOptionsMenu() {
        setHasOptionsMenu(true);
    }

    private void initFab(View root) {
        mFab = root.findViewById(R.id.fab_purchases);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getFragmentManager() != null) {
                    new AddPurchaseFragment().show(getFragmentManager(), FRAGMENT_DIALOG);
                }
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
            mPurchasesViewModel = ViewModelProviders.of(getActivity()).get(PurchasesViewModel.class);
            mPurchasesViewModel.getAllPurchases().observe(this, new Observer<List<Purchase>>() {
                @Override
                public void onChanged(List<Purchase> purchases) {
                    mPurchasesAdapter.setPurchases(purchases);
                }
            });
        }
    }
}
