package com.wenote.pay

import com.wenote.pay.chain.RealChain
import com.wenote.pay.interceptor.Interceptor
import com.wenote.pay.log.debug

internal class ReadPayInterceptor:Interceptor {
    override fun intercept(chain: RealChain) {
        chain.orderManager.status.payInfoHashCode = chain.orderManager.payInfo.hashCode()
        debug("计算支付payInfo hash ${chain.orderManager.status.payInfoHashCode}")

        chain.callNext()
    }

    override fun onResume() {

    }

    override fun onDestroy() {

    }
}