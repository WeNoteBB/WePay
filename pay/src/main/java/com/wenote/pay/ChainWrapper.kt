package com.wenote.pay

import com.wenote.pay.chain.RealChain
import com.wenote.pay.channel.PayChannelManager
import com.wenote.pay.dialog.interceptor.IDialogInterceptor
import com.wenote.pay.interceptor.Interceptor
import com.wenote.pay.lifecycle.LifeCycleManager
import com.wenote.pay.order.OrderManager
import com.wenote.pay.pay.IPayInterceptor
import com.wenote.pay.pay.PayResultInterceptor

internal class ChainWrapper(val payInterceptor: IPayInterceptor,
                            val payResultInterceptor: PayResultInterceptor,
                            val dialogInterceptor: IDialogInterceptor,
                            val orderInterceptor: Interceptor,
                            val orderManager: OrderManager,
                            val contextProvider: ContextProvider,
                            val payPayChannelManager: PayChannelManager,
                            val lifeCycleManager: LifeCycleManager,
                            val iPayResult: IPayResult) {

    private var realChain: RealChain
    private val readPayInterceptor = ReadPayInterceptor()

    init {
        realChain = createRealChain(orderManager, payPayChannelManager, iPayResult, contextProvider)
    }

    private fun createRealChain(orderManager: OrderManager,
                                paymentPayChannel: PayChannelManager,
                                iPayResult: IPayResult,
                                contentProvider: ContextProvider): RealChain {

        val realChain = RealChain(
                contextProvider = contentProvider,
                payChannelManager = paymentPayChannel,
                orderManager = orderManager,
                iPayResult = iPayResult)

        return insertChain(realChain)
    }

    private fun insertChain(realChain: RealChain): RealChain {
        realChain.clear()

        lifeCycleManager.addLifecycleObserver(realChain.addInterceptor(dialogInterceptor))
        lifeCycleManager.addLifecycleObserver(realChain.addInterceptor(orderInterceptor))
        lifeCycleManager.addLifecycleObserver(realChain.addInterceptor(readPayInterceptor))
        lifeCycleManager.addLifecycleObserver(realChain.addInterceptor(payInterceptor))
        lifeCycleManager.addLifecycleObserver(realChain.addInterceptor(payResultInterceptor))
        lifeCycleManager.addLifecycleObserver(contextProvider)

        return realChain
    }

    fun callNext() {
        realChain.callNext()
    }

}