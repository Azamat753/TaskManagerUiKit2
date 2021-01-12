package com.lawlett.taskmanageruikit.settings.job_to_get_done.view_model;


public class ThemeViewModel {

    private int imageColorSet;
    private boolean isChanged;

    public ThemeViewModel() {
    }

    public ThemeViewModel(int imageColorSet, boolean isChanged) {
        this.imageColorSet = imageColorSet;
        this.isChanged = isChanged;
    }


    public int getImageColorSet() {
        return imageColorSet;
    }

    public void setImageColorSet(int imageColorSet) {
        this.imageColorSet = imageColorSet;
    }

    public boolean isChanged() {
        return isChanged;
    }

    public void     setChanged(boolean changed) {
        isChanged = changed;
    }
}
