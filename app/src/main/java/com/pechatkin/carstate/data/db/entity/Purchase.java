package com.pechatkin.carstate.data.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "purchases_table")
public class Purchase {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "description")
    private String mDescription;

    @ColumnInfo(name = "is_history")
    private boolean mIsHistory;

    @ColumnInfo(name = "add_purchases_date")
    private String mAddPurchasesDate;

    @ColumnInfo(name = "add_history_date")
    private String mAddHistoryDate;

    @ColumnInfo(name = "prise")
    private float mPrise;

    @ColumnInfo(name = "category")
    private String mCategory;

    public Purchase(String title, String description, String addPurchasesDate, float prise, String category) {
        mTitle = title;
        mDescription = description;
        mIsHistory = false;
        mAddPurchasesDate = addPurchasesDate;
        mPrise = prise;
        mCategory = category;
    }

    public long getId() { return mId; }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public boolean isIsHistory() {
        return mIsHistory;
    }

    public String getAddPurchasesDate() {
        return mAddPurchasesDate;
    }

    public String getAddHistoryDate() {
        return mAddHistoryDate;
    }

    public float getPrise() {
        return mPrise;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setId(long id) {
        mId = id;
    }

    public void setIsHistory(boolean isHistory) {
        mIsHistory = isHistory;
    }

    public void setAddHistoryDate(String addHistoryDate) {
        mAddHistoryDate = addHistoryDate;
    }
}
