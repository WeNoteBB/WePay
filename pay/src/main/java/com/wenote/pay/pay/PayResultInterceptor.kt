package com.wenote.pay.pay

import com.wenote.pay.chain.RealChain
import com.wenote.pay.order.OrderStatus
import com.wenote.pay.order.PayResult

open class PayResultInterceptor:IPayResultInterceptor {

    override fun intercept(chain: RealChain) {
        var orderStatus = getPayResult(chain)

        when(orderStatus.result) {
            PayResult.FALSE -> chain.iPayResult.getOrderResult(orderStatus)
        }
    }

    override fun onResume() {
    }

    override fun onDestroy() {

    }

    override fun getPayResult(chain: RealChain):OrderStatus {
        return chain.orderManager.status
    }
}