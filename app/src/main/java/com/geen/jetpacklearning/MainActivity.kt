package com.geen.jetpacklearning

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.geen.jetpacklearning.databinding.ActivityMainBinding

class MainActivity :AppCompatActivity(){

    var message:String? = null
    var count = 5
    lateinit var mBinding : ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)

        generateAnswerString(2)

        Toast.makeText(this,generateAnswerString(2),Toast.LENGTH_LONG).show()

        val length = stringLengthFunc("test")
        Toast.makeText(this,length.toString(),Toast.LENGTH_LONG).show()

        AppHelper.launchAppDetail(this,"","com.cashcase.loans")
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

}