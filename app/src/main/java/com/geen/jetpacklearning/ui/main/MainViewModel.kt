package com.geen.jetpacklearning.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private var mCurrentName: MutableLiveData<String>? = null

    fun getCurrentName(): MutableLiveData<String>? {
        if (mCurrentName == null) {
            mCurrentName = MutableLiveData()
        }
        mCurrentName?.value = "xxxx"
        return mCurrentName
    }

}