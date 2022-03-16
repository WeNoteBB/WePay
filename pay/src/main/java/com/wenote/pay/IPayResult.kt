package com.wenote.pay

import com.wenote.pay.order.OrderStatus

interface IPayResult {
    fun getOrderResult(orderStatus: OrderStatus)
}