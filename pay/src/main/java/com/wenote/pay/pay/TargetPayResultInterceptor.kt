package com.wenote.pay.pay

import com.wenote.pay.chain.RealChain
import com.wenote.pay.order.OrderStatus

class TargetPayResultInterceptor(val interceptors:HashMap<String, IPayResultInterceptor>): PayResultInterceptor() {
    private var aDefault:IPayResultInterceptor? = null

    override fun intercept(chain: RealChain) {
        val channel = chain.payChannelManager.getDefaultChannel()?:return
        aDefault = interceptors[channel.code]
        aDefault?.intercept(chain)
    }

    override fun getPayResult(chain: RealChain): OrderStatus {
        return aDefault!!.getPayResult(chain)
    }

    override fun onResume() {
        aDefault?.onResume()
    }

    override fun onDestroy() {
        aDefault?.onDestroy()
    }
}