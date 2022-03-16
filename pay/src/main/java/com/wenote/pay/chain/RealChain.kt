package com.wenote.pay.chain

import com.wenote.pay.ContextProvider
import com.wenote.pay.IPayResult
import com.wenote.pay.channel.PayChannelManager
import com.wenote.pay.interceptor.Interceptor
import com.wenote.pay.log.debug
import com.wenote.pay.order.OrderManager
import com.wenote.pay.order.PayResult

class RealChain constructor(val contextProvider: ContextProvider,
                            val payChannelManager: PayChannelManager,
                            val orderManager:OrderManager,
                            val iPayResult:IPayResult){
    private var index: Int = 0;

    private val chain: ArrayList<Interceptor> = ArrayList()


    fun addInterceptor(interceptor: Interceptor?) :Interceptor?{
        interceptor?.let {
            chain.add(interceptor)
        }

        return interceptor
    }

    fun clear() {
        if (chain.size == 0) return

        for (interceptor in chain) {
            interceptor.onDestroy()
        }

        chain.clear()
    }

    fun getInterceptor(): ArrayList<Interceptor> {
        return chain
    }

    fun callNext(): Interceptor? {
        val interceptor = getNextInterceptor()
        interceptor?.intercept(this)
        return interceptor
    }

    private fun getNextInterceptor(): Interceptor? {
        if (index > chain.size - 1) {
            debug("chain is end and index is $index")
            // 触发回调
            iPayResult.getOrderResult(orderStatus = orderManager.status)
            return null
        }

        val id = index++
        return chain[id]
    }
}