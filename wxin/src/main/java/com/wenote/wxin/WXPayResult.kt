package com.wenote.pay.pay.wxin

import com.wenote.pay.order.OrderStatus
import com.wenote.pay.order.PayResult

class WXPayResult{
    var payResult = OrderStatus(-1, "", PayResult.FALSE, "WXIN")
}
object WXPayInfoHash {
    var hash:Int = 0
}