package com.geen.jetpacklearning.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geen.jetpacklearning.bean.UserInfo
import com.geen.jetpacklearning.bean.UserModel


class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private var mCurrentName: MutableLiveData<String>? = null
    var mUserModel: MutableLiveData<UserModel>? = null
    var userModel : UserModel? = null

    fun getCurrentName(): MutableLiveData<String>? {
        if (mCurrentName == null) {
            mCurrentName = MutableLiveData()
        }
        mCurrentName?.value = "current"
        return mCurrentName
    }

    fun getUserModel(): MutableLiveData<UserModel>? {
        if (mUserModel == null) {
            mUserModel = MutableLiveData()
        }
        userModel = UserModel("xxx", 1)
        mUserModel?.value = userModel
        return mUserModel
    }

    fun updateModel(userModel: UserModel?) : MutableLiveData<UserModel>? {
        if (mUserModel == null) {
            mUserModel = MutableLiveData()
        }
        mUserModel?.value = userModel
        return mUserModel

    }

}