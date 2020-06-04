package com.lawlett.taskmanageruikit.utils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QuickViewModel extends ViewModel {
    private Integer mCounter = 0;
    public MutableLiveData<Integer> counter = new MutableLiveData<>();


   public void onIncrementClick() {
        mCounter++;
        counter.setValue(mCounter);
    }
}
