package com.wenote.pay.lifecycle

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class LifeCycleManager {

    // 生命周期管理
    private var lifecycleFragment: LifecycleFragment? = null

    ///生命周期管理
    internal fun with(activity: AppCompatActivity): LifeCycleManager {
        if (lifecycleFragment == null) {
            val fm = activity.supportFragmentManager;
            lifecycleFragment = getFragment(fm)
        }

        return this
    }

    ///生命周期管理
    internal fun with(fragment: Fragment): LifeCycleManager {
        if (lifecycleFragment == null) {
            val fm = fragment.childFragmentManager
            lifecycleFragment = getFragment(fm)
        }

        return this
    }


    ///生命周期管理
    private fun getFragment(fm: FragmentManager): LifecycleFragment {
        var fragment = fm.findFragmentByTag(LifecycleFragment.TAG)
        if (fragment == null) {
            fragment = LifecycleFragment()
            fm.beginTransaction().add(fragment, LifecycleFragment.TAG).commitAllowingStateLoss();
        }

        return fragment as LifecycleFragment
    }

    // 添加生命周期
    internal fun addLifecycleObserver(lifecycle: Lifecycle?): LifeCycleManager {
        lifecycle?.let {
            lifecycleFragment?.addLifecycle(lifecycle)

        }
        return this
    }

}