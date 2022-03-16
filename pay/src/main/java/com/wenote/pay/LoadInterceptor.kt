package com.wenote.pay

import com.wenote.pay.channel.PayChannelManager
import com.wenote.pay.pay.IPayInterceptor
import com.wenote.pay.pay.IPayResultInterceptor

internal class LoadInterceptor(val payChannelManager: PayChannelManager) {
    
    fun loadPayInterceptors() :HashMap<String, IPayInterceptor> {
        val map = HashMap<String, IPayInterceptor>()
        
        for (interceptor in payChannelManager.getPaymentChannel()) {
            map[interceptor.key]=  interceptor.value.payInterceptor
        }

        return map
    }

    fun loadPayResultInterceptors() :HashMap<String, IPayResultInterceptor> {
        val map = HashMap<String, IPayResultInterceptor>()

        for (interceptor in payChannelManager.getPaymentChannel()) {
            map[interceptor.key]=  interceptor.value.payResultInterceptor
        }

        return map
    }
}