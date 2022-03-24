package com.wenote.pay.pay.wxin

import com.wenote.pay.order.OrderStatus
import com.wenote.pay.order.PayResult
import java.util.concurrent.CountDownLatch

class WXPayResult {
    var payResult = OrderStatus(-1, "", PayResult.FALSE, "WXIN")
}

object WXPayEvent {

    private val data = HashMap<Int, WXEvent>()

    fun get(hash:Int, result: WXPayResult):WXEvent?{
        if (data.containsKey(hash)) {
            return data[hash]
        }else{
            val event = WXEvent(result)
            data[hash] =  event

            return event
        }
    }
}

class WXEvent(result: WXPayResult) {
    val countDownLatch = CountDownLatch(1)
}

object WXPayInfoHash {
    var hash: Int = 0
}