package com.wenote.pay.log

import android.util.Log
import com.wenote.pay.BuildConfig

class PayLog {
    companion object {
        val TAG = "zhangPay"
        fun d(tag: String, msg: String) {
            if (BuildConfig.DEBUG) {
                Log.d(tag, msg)
            }
        }

        fun d(msg: String) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, msg)
            }
        }
    }
}

fun debug(msg: String) {
    PayLog.d(msg)
}