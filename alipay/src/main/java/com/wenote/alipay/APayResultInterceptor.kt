package com.wenote.apay

import com.wenote.pay.chain.RealChain
import com.wenote.pay.pay.PayResultInterceptor

class APayResultInterceptor: PayResultInterceptor() {
    override fun intercept(chain: RealChain) {
        chain.iPayResult.getOrderResult(chain.orderManager.status)
    }
}