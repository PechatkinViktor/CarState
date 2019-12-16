package com.pechatkin.carstate.presentation.ui.tutorial;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pechatkin.carstate.R;
import com.pechatkin.carstate.domain.model.ViewPagerItem;

import java.util.ArrayList;
import java.util.List;

public class TutorialAdapter extends RecyclerView.Adapter<TutorialAdapter.TutorialItemHolder> {

    private List<ViewPagerItem> mPagerItems = new ArrayList<>();

    @NonNull
    @Override
    public TutorialItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TutorialItemHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.view_pager_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TutorialItemHolder holder, int position) {
        holder.bindView(mPagerItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mPagerItems.size();
    }

    void setPagerItems(@NonNull List<ViewPagerItem> pagerItems) {
        mPagerItems.addAll(pagerItems);
    }

    class TutorialItemHolder extends RecyclerView.ViewHolder {

        private final ImageView mViewPagerImage;
        private final TextView mViewPagerTitle;
        private final TextView mViewPagerDesc;

        TutorialItemHolder(@NonNull View itemView) {
            super(itemView);

            mViewPagerImage = itemView.findViewById(R.id.view_pager_item_image);
            mViewPagerTitle = itemView.findViewById(R.id.view_pager_item_title);
            mViewPagerDesc = itemView.findViewById(R.id.view_pager_item_desc);
        }

        private void bindView(@NonNull ViewPagerItem viewPagerItem ) {
            mViewPagerImage.setImageResource(viewPagerItem.getImage());
            mViewPagerTitle.setText(viewPagerItem.getTitle());
            mViewPagerDesc.setText(viewPagerItem.getDescription());
        }
    }
}
