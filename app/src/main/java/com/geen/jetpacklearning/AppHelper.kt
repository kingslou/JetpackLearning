package com.geen.jetpacklearning

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.util.Log

object AppHelper {


    /***
     * 跳转到google play
     * appPkg 是应用的包名
     * @param context
     * @param channel
     * @param appPkg
     */
    fun launchAppDetail(
        context: Context,
        channel: String?,
        appPkg: String
    ) {
        var channel = channel
        if (TextUtils.isEmpty(channel)) {
            channel = "com.android.vending"
        }
        try {
            if (TextUtils.isEmpty(appPkg)) {
                return
            }
            val uri = Uri.parse("market://details?id=$appPkg")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage(channel)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.e("跳转异常",e.toString())
        }
    }


}