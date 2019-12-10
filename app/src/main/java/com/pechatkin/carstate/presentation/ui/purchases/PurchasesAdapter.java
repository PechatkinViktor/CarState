package com.pechatkin.carstate.presentation.ui.purchases;

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

public class PurchasesAdapter extends RecyclerView.Adapter<PurchasesAdapter.PurchasesHolder> {


    private OnItemClickListener mClickListener;
    private List<Purchase> mPurchases = new ArrayList<>();

    @NonNull
    @Override
    public PurchasesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.purchases_view_item, parent, false);
        return new PurchasesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchasesHolder holder, int position) {
        holder.bindView(mPurchases.get(position));
    }

    @Override
    public int getItemCount() {
        return mPurchases.size();
    }

    Purchase getPurchaseAt(int position) {
        return mPurchases.get(position);
    }

    void setPurchases(List<Purchase> purchases) {
        PurchaseDiffCallback diffCallback = new PurchaseDiffCallback(mPurchases, purchases);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        mPurchases.clear();
        mPurchases.addAll(purchases);
        diffResult.dispatchUpdatesTo(this);
        notifyDataSetChanged();
    }


    class PurchasesHolder extends RecyclerView.ViewHolder {

        private final TextView mPurchaseTitle;
        private final TextView mPurchaseDate;
        private final TextView mPurchaseCategory;
        private final TextView mPurchaseDesc;
        private final TextView mPurchasePrise;

        PurchasesHolder(@NonNull View itemView) {
            super(itemView);

            mPurchaseTitle = itemView.findViewById(R.id.text_view_purchases_title);
            mPurchaseDate = itemView.findViewById(R.id.text_view_purchases_date);
            mPurchaseCategory = itemView.findViewById(R.id.text_view_purchases_category);
            mPurchaseDesc = itemView.findViewById(R.id.text_view_purchases_desc);
            mPurchasePrise = itemView.findViewById(R.id.text_view_purchases_prise);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(mClickListener != null && position != RecyclerView.NO_POSITION) {
                        mClickListener.onItemClick(mPurchases.get(position));
                    }
                }
            });
        }

        private void bindView(Purchase purchase) {
            mPurchaseTitle.setText(purchase.getTitle());
            mPurchaseDate.setText(purchase.getAddPurchasesDate());
            mPurchaseCategory.setText(purchase.getCategory());
            mPurchaseDesc.setText(purchase.getDescription());
            mPurchasePrise.setText(String.valueOf(purchase.getPrise()));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Purchase purchase);
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        mClickListener = listener;
    }
}
