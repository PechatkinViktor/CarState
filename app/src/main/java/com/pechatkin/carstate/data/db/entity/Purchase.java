package com.pechatkin.carstate.data.db.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "purchases_table")
public class Purchase implements Parcelable {

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

    public Purchase(String title, String description, String addPurchasesDate,
                    float prise, String category) {
        mTitle = title;
        mDescription = description;
        mIsHistory = false;
        mAddPurchasesDate = addPurchasesDate;
        mPrise = prise;
        mCategory = category;
    }

    protected Purchase(Parcel in) {
        mId = in.readLong();
        mTitle = in.readString();
        mDescription = in.readString();
        mIsHistory = in.readByte() != 0;
        mAddPurchasesDate = in.readString();
        mAddHistoryDate = in.readString();
        mPrise = in.readFloat();
        mCategory = in.readString();
    }

    public static final Creator<Purchase> CREATOR = new Creator<Purchase>() {
        @Override
        public Purchase createFromParcel(Parcel in) {
            return new Purchase(in);
        }

        @Override
        public Purchase[] newArray(int size) {
            return new Purchase[size];
        }
    };

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

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setAddPurchasesDate(String addPurchasesDate) {
        mAddPurchasesDate = addPurchasesDate;
    }

    public void setPrise(float prise) {
        mPrise = prise;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return mId == purchase.mId &&
                mIsHistory == purchase.mIsHistory &&
                Float.compare(purchase.mPrise, mPrise) == 0 &&
                mTitle.equals(purchase.mTitle) &&
                mDescription.equals(purchase.mDescription) &&
                mAddPurchasesDate.equals(purchase.mAddPurchasesDate) &&
                Objects.equals(mAddHistoryDate, purchase.mAddHistoryDate) &&
                mCategory.equals(purchase.mCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mTitle, mDescription, mIsHistory,
                mAddPurchasesDate, mAddHistoryDate, mPrise, mCategory);
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "mId=" + mId +
                ", mTitle='" + mTitle + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mIsHistory=" + mIsHistory +
                ", mAddPurchasesDate='" + mAddPurchasesDate + '\'' +
                ", mAddHistoryDate='" + mAddHistoryDate + '\'' +
                ", mPrise=" + mPrise +
                ", mCategory='" + mCategory + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mId);
        parcel.writeString(mTitle);
        parcel.writeString(mDescription);
        parcel.writeByte((byte) (mIsHistory ? 1 : 0));
        parcel.writeString(mAddPurchasesDate);
        parcel.writeString(mAddHistoryDate);
        parcel.writeFloat(mPrise);
        parcel.writeString(mCategory);
    }
}
