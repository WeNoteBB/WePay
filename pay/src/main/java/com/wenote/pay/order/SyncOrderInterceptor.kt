package com.wenote.pay.order

import androidx.annotation.MainThread
import com.wenote.pay.chain.RealChain
import com.wenote.pay.interceptor.Interceptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

// 同步的请求拦截器
abstract class SyncOrderInterceptor : Interceptor {
    private var mChain: RealChain? = null
    private val scope = MainScope()

    override fun intercept(chain: RealChain) {
        mChain = chain
        chain.payChannelManager.getDefaultChannel()?.code?.let {
            handlerNext(it, chain)
        }
    }

    private fun handlerNext(channelCode: String, chain: RealChain) {
        scope.launch(Dispatchers.IO) {
            val info = createOrder(channelCode, chain)
            updateOrderManager(info)
            handlerNext(chain)
        }
    }

    protected abstract suspend fun createOrder(channelCode: String, realChain: RealChain): OrderManager

    @MainThread
    private fun handlerNext(chain: RealChain) {
        chain.callNext()
    }

    private fun updateOrderManager(info: OrderManager) {
       mChain?.orderManager?.run {
           payInfo = info.payInfo
           orderId = info.orderId
           status = info.status

       }
    }

    override fun onResume() {

    }

    override fun onDestroy() {
        scope.cancel()
        mChain = null
    }
}