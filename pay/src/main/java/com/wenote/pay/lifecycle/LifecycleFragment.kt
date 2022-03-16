package com.wenote.pay.lifecycle

import androidx.fragment.app.Fragment

internal class LifecycleFragment : Fragment() {

    companion object {
        val TAG = "LF"
    }

    private val lifecycleCollection = ArrayList<Lifecycle>()

    fun addLifecycle(lifecycle: Lifecycle?) {
        lifecycle?.let {
            lifecycleCollection.add(lifecycle)
        }
    }

    override fun onResume() {
        super.onResume()
        for (lifecycle in lifecycleCollection) {
            lifecycle.onResume()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        for (lifecycle in lifecycleCollection) {
            lifecycle.onDestroy()
        }
    }
}