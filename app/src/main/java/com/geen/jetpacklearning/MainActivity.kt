package com.geen.jetpacklearning

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.geen.jetpacklearning.bean.UserInfo
import com.geen.jetpacklearning.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.net.URL
import java.net.URLConnection
import java.util.*

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

        test()

        Log.e("xxx","协程之后"+ Date())

        MyThreadTest.notifyThreadWithVolatile()
    }


    fun test(){
        CoroutineScope(Dispatchers.Main).launch {
            val bitmap = withContext(Dispatchers.IO){
                getImageBitmap()
            }

            var def = async(Dispatchers.IO) {
                getImageBitmap()
            }

            Log.e("xxx","请求之后"+ Date())
            mBinding.imageView.setImageBitmap(bitmap)

            mBinding.imageView1.setImageBitmap(def.await())
        }
    }

    fun getImageBitmap():Bitmap{
        val url = URL("https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png")
        val urlConnection : URLConnection = url.openConnection()
        urlConnection.connectTimeout = 5000
        urlConnection.connect()
        val stream = urlConnection.getInputStream()
        return BitmapFactory.decodeStream(stream)
    }

    fun clickIntent(){
        userInfo.userName.set("0000")
        Toast.makeText(this,userInfo.userName.get(),Toast.LENGTH_LONG).show()

        startActivity(Intent(this,LearnViewModelActivity::class.java))
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