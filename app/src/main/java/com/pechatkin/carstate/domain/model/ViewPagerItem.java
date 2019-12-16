package com.pechatkin.carstate.domain.model;

import java.util.Objects;

public class ViewPagerItem {

    private final int mImage;
    private final String mTitle;
    private final String mDescription;


    public ViewPagerItem(int image, String title, String description) {
        mImage = image;
        mTitle = title;
        mDescription = description;
    }

    public int getImage() {
        return mImage;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ViewPagerItem that = (ViewPagerItem) o;
        return mImage == that.mImage &&
                Objects.equals(mTitle, that.mTitle) &&
                Objects.equals(mDescription, that.mDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mImage, mTitle, mDescription);
    }

    @Override
    public String toString() {
        return "ViewPagerItem{" +
                "mImage=" + mImage +
                ", mTitle='" + mTitle + '\'' +
                ", mDescription='" + mDescription + '\'' +
                '}';
    }
}
