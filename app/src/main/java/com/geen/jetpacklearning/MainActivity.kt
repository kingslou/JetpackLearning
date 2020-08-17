package com.geen.jetpacklearning

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.geen.jetpacklearning.bean.UserInfo
import com.geen.jetpacklearning.databinding.ActivityMainBinding

class MainActivity :AppCompatActivity(){

    var message:String? = null
    var count = 5
    lateinit var mBinding : ActivityMainBinding;

    lateinit var userInfo : UserInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        generateAnswerString(2)

        Toast.makeText(this,generateAnswerString(2),Toast.LENGTH_LONG).show()

        val length = stringLengthFunc(todo("xxx"))
        Toast.makeText(this,length.toString(),Toast.LENGTH_LONG).show()

        userInfo = UserInfo()

        userInfo.userAge.set(18)
        userInfo.userName.set("xxx")


        mBinding.userTest = userInfo

        mBinding.aty  = this

    }

    fun clickIntent(){
        userInfo.userName.set("0000")
        Toast.makeText(this,userInfo.userName.get(),Toast.LENGTH_LONG).show()
    }

    fun generateAnswerString(countThreshold: Int): String = if (count > countThreshold) {
        "I have the answer"
    } else {
        "The answer eludes me"
    }

    val stringLengthFunc: (String) -> Int = { input ->
        input.length
    }

    fun getlength(input:String):Int{

        return 0
    }

    val test:(String)-> Int = { it->

        it.length
    }
    
    fun todo(text:String):String{

        return text
    }



}