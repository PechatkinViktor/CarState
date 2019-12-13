package com.pechatkin.carstate.presentation.ui.history;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.pechatkin.carstate.R;
import com.pechatkin.carstate.data.db.entity.Purchase;
import com.pechatkin.carstate.presentation.ui.utils.PurchaseDiffCallback;

import java.util.ArrayList;
import java.util.List;

import static com.pechatkin.carstate.presentation.ui.utils.Const.DATE_FORMAT;
import static com.pechatkin.carstate.presentation.ui.utils.Const.PRISE_FORMAT;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder>{

    private OnItemClickListener mClickListener;
    private List<Purchase> mPurchasesInHistory = new ArrayList<>();

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);
        return new HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder, int position) {
        holder.bindView(mPurchasesInHistory.get(position));
    }

    @Override
    public int getItemCount() {
        return mPurchasesInHistory.size();
    }

    void addPurchase(Purchase updatedPurchase) {
        mPurchasesInHistory.add(updatedPurchase);
    }

    Purchase getPurchaseAt(int position) {
        return mPurchasesInHistory.get(position);
    }

    void setPurchases(List<Purchase> purchases) {
        PurchaseDiffCallback diffCallback = new PurchaseDiffCallback(mPurchasesInHistory, purchases);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        mPurchasesInHistory.clear();
        mPurchasesInHistory.addAll(purchases);
        diffResult.dispatchUpdatesTo(this);
    }



    class HistoryHolder extends RecyclerView.ViewHolder {

        private final TextView mPurchaseTitle;
        private final TextView mPurchaseDateInHistory;
        private final TextView mPurchaseCategory;
        private final TextView mPurchaseDesc;
        private final TextView mPurchasePrise;

        HistoryHolder(@NonNull View itemView) {
            super(itemView);

            mPurchaseTitle = itemView.findViewById(R.id.text_view_purchases_title);
            mPurchaseDateInHistory = itemView.findViewById(R.id.text_view_purchases_date);
            mPurchaseCategory = itemView.findViewById(R.id.text_view_purchases_category);
            mPurchaseDesc = itemView.findViewById(R.id.text_view_purchases_desc);
            mPurchasePrise = itemView.findViewById(R.id.text_view_purchases_prise);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if(mClickListener != null && position != RecyclerView.NO_POSITION) {
                    mClickListener.onItemClick(mPurchasesInHistory.get(position));
                }
            });
        }

        @SuppressLint("DefaultLocale")
        void bindView(Purchase purchase) {
            mPurchaseTitle.setText(purchase.getTitle());
            mPurchaseDateInHistory.setText(String.format(DATE_FORMAT, purchase.getAddHistoryDate()));
            mPurchaseCategory.setText(purchase.getCategory());
            mPurchaseDesc.setText(purchase.getDescription());
            mPurchasePrise.setText(String.format(PRISE_FORMAT, purchase.getPrise()));
        }
    }


    public interface OnItemClickListener {
        void onItemClick(Purchase purchase);
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        mClickListener = listener;
    }
}
