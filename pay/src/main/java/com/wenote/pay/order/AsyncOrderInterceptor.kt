package com.wenote.pay.order

import android.os.Handler
import com.wenote.pay.chain.RealChain
import com.wenote.pay.interceptor.Interceptor

abstract class AsyncOrderInterceptor : Interceptor {

    private var handler: Handler
    private var mChain: RealChain? = null
    private val what = 0x01

    init {
        handler = Handler { msg ->
            if (msg?.what == what) {
                mChain?.callNext()
            }
            true
        }
    }

    // 创建订单
    protected abstract fun createOrder(channelCode: String)

    // 完成请求时调用
    protected open fun handlerNext() {
        handler.sendEmptyMessage(what)
    }

    override fun intercept(chain: RealChain) {
        mChain = chain
        if (mChain?.orderManager?.status?.result == PayResult.SUCCESS) {
            chain.payChannelManager.getDefaultChannel()?.code?.let {
                createOrder(it)
            }
        }else {
            chain.callNext()
        }

    }

    protected open fun updateOrderManager(info: OrderManager) {
        mChain?.orderManager?.run {
            payInfo = info.payInfo
            orderId = info.orderId
            status = info.status
        }
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        mChain = null
    }
}