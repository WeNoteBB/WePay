package com.wenote.pay

import androidx.appcompat.app.AppCompatActivity
import com.wenote.pay.lifecycle.Lifecycle
import java.lang.ref.WeakReference

class ContextProvider(activity: AppCompatActivity) : Lifecycle {
    @Volatile
    var onDestroy = false

    private var activity: AppCompatActivity? = null
    private var weakActivity: WeakReference<AppCompatActivity>? = null

    init {
        this.activity = activity
    }

    fun getActivity(): WeakReference<AppCompatActivity>? {
        return if (onDestroy) {
            null
        } else {
            if (weakActivity == null || weakActivity!!.get() == null) {
                weakActivity = WeakReference(activity)
            }
            weakActivity
        }
    }

    override fun onResume() {
        onDestroy = false
    }

    override fun onDestroy() {
        onDestroy = true
        activity = null
    }

}