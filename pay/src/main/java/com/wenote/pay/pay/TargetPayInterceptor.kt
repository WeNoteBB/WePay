package com.wenote.pay.pay

import com.wenote.pay.chain.RealChain
import com.wenote.pay.interceptor.Interceptor
import com.wenote.pay.log.debug
import com.wenote.pay.order.PayResult

class TargetPayInterceptor(val payInterceptors: HashMap<String, IPayInterceptor>) : IPayInterceptor {
    private var default: Interceptor? = null


    override fun intercept(chain: RealChain) {
        val channel = chain.payChannelManager.getDefaultChannel() ?: return
        if (chain.orderManager.status.result == PayResult.FALSE) {
            debug("创建订单拦截器的支付结果为false, 直接调用下一个拦截器")
            chain.callNext()?.intercept(chain)
            return
        } else {
            default = payInterceptors[channel.code]
            default?.intercept(chain)
        }

    }

    override fun onResume() {
        default?.onResume()
    }

    override fun onDestroy() {
        default?.onDestroy()
    }
}