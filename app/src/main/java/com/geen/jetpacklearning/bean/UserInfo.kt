package com.geen.jetpacklearning.bean

import androidx.databinding.Observable
import androidx.databinding.ObservableField

class UserInfo constructor(var userName:ObservableField<String> = ObservableField(""), var userAge :ObservableField<Int> = ObservableField(0)) {


}