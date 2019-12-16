package com.pechatkin.carstate.data.tutorial;

import android.content.Context;

import androidx.annotation.NonNull;

import com.pechatkin.carstate.R;
import com.pechatkin.carstate.domain.model.ViewPagerItem;

import java.util.ArrayList;
import java.util.List;

public class TutorialDataProvider {

    private Context mContext;

    public TutorialDataProvider(@NonNull Context context) {
        mContext = context;
    }

    public List<ViewPagerItem> getTutorialData() {
        List<ViewPagerItem> items = new ArrayList<>();

        items.add(new ViewPagerItem(R.drawable.vp_img_one,
                mContext.getString(R.string.tutorial_item_one_title),
                mContext.getString(R.string.tutorial_item_one_desc)));
        items.add(new ViewPagerItem(R.drawable.vp_img_two,
                mContext.getString(R.string.tutorial_item_two_title),
                mContext.getString(R.string.tutorial_item_two_desс)));
        items.add(new ViewPagerItem(R.drawable.vp_img_three,
                mContext.getString(R.string.tutorial_item_three_title),
                mContext.getString(R.string.tutorial_item_three_desc)));

        return items;

    }
}
