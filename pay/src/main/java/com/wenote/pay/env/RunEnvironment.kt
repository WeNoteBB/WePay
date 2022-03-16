package com.wenote.pay.env

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.wenote.pay.PayHandler
import com.wenote.pay.log.debug

class RunEnvironment constructor(val release: Boolean) {

    fun with(fragment: Fragment): PayHandler {
        debug("with fragment")
        var handler: PayHandler? = null

        handler = PayHandler(release)
        handler.with(fragment)

        return handler
    }

    fun with(activity: AppCompatActivity): PayHandler {
        debug("with activity")
        var handler: PayHandler? = null

        handler = PayHandler(release)
        handler.with(activity)


        return handler
    }
}