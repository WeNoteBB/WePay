package com.wenote.pay.pay.wxin

import com.wenote.pay.chain.RealChain
import com.wenote.pay.order.PayResult
import com.wenote.pay.pay.PayResultInterceptor


class WXResultInterceptor : PayResultInterceptor() {

    override fun intercept(chain: RealChain) {
        if (chain.orderManager.status.result == PayResult.FALSE) {
            chain.iPayResult.getOrderResult(chain.orderManager.status)
        } else {

        }
//        RxBus.getInstance().toObservable(WXPayResult::class.java).subscribe {
//            debug("接受到微信支付的返回结果 ${chain.orderManager.status.msg} ${chain.orderManager.status.code}")
//            chain.orderManager.payInfo.hashCode().run {
//                // 校驗hash，是否為當前的支付
//                if (this != 0 && this == it.payResult.payInfoHashCode) {
//                    it.payResult.result = PayResult.SUCCESS
//                    it.payResult.code = 0
//                    it.payResult.msg = "支付成功"
//                    chain.iPayResult.getOrderResult(it.payResult)
//                } else {
//                    chain.iPayResult.getOrderResult(OrderStatus(-1, "未找到订单", PayResult.FALSE,
//                            "WXIN", -1))
//                }
//            }
//        }
    }

    override fun onResume() {

    }

    override fun onDestroy() {

    }

}
