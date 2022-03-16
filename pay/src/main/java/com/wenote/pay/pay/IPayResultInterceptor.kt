package com.wenote.pay.pay

import com.wenote.pay.chain.RealChain
import com.wenote.pay.interceptor.Interceptor
import com.wenote.pay.order.OrderStatus

interface IPayResultInterceptor:Interceptor {

    fun getPayResult(chain: RealChain): OrderStatus
}