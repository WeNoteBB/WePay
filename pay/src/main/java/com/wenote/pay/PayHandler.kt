package com.wenote.pay

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.wenote.pay.channel.Channel
import com.wenote.pay.channel.PayChannelManager
import com.wenote.pay.dialog.interceptor.DialogInterceptor
import com.wenote.pay.interceptor.Interceptor
import com.wenote.pay.lifecycle.LifeCycleManager
import com.wenote.pay.log.debug
import com.wenote.pay.order.AsyncOrderInterceptor
import com.wenote.pay.order.SyncOrderInterceptor
import com.wenote.pay.order.OrderManager
import com.wenote.pay.pay.IPayInterceptor
import com.wenote.pay.pay.PayResultInterceptor
import com.wenote.pay.pay.TargetPayInterceptor
import com.wenote.pay.pay.TargetPayResultInterceptor


class PayHandler(release: Boolean) {

    private lateinit var contextProvider: ContextProvider

    private var payChannelManager: PayChannelManager = PayChannelManager(release)
    private var orderManager: OrderManager = OrderManager()
    private var lifecycleManager: LifeCycleManager = LifeCycleManager()

    private lateinit var chainWrapper: ChainWrapper

    private lateinit var orderInterceptor: Interceptor
    private lateinit var payInterceptor: IPayInterceptor
    private lateinit var payResultInterceptor: PayResultInterceptor
    private lateinit var dialogInterceptor: DialogInterceptor

    private var result = 0

    init {
        for (channel in WPay.getDefaultChannel()) {
            addChannel(channel.copy())
        }
    }

    fun withPayInfo(msg: String): PayHandler {
        orderManager.payDesc = msg
        return this
    }

    fun withPayFee(fee: Int): PayHandler {
        orderManager.payFee = fee
        return this
    }

    fun removeChannel(key: String): PayHandler {
        payChannelManager.removeDefaultChannel(key)
        return this
    }

    fun addChannel(channel: Channel): PayHandler {
        payChannelManager.addChannel(channel)
        debug("添加支付渠道 $payChannelManager $channel ${channel.code} ${channel.channelStatus}")
        return this
    }

    internal fun with(fragment: Fragment): PayHandler {
        lifecycleManager.with(fragment)
        contextProvider = ContextProvider(fragment.activity as AppCompatActivity)
        return this
    }

    internal fun with(activity: AppCompatActivity): PayHandler {
        contextProvider = ContextProvider(activity)
        lifecycleManager.with(activity)
        return this
    }

    fun addInterceptor(syncOrderInterceptor: SyncOrderInterceptor): PayHandler {
        this.orderInterceptor = syncOrderInterceptor
        result = result or 0x10
        return this
    }

    fun addInterceptor(syncOrderInterceptor: AsyncOrderInterceptor): PayHandler {
        this.orderInterceptor = syncOrderInterceptor
        result = result or 0x10
        return this
    }

    fun call(iPayResult: IPayResult) {
        debug("PayHandler call method $this")
        dialogInterceptor = DialogInterceptor(payChannelManager, orderManager)
        result = result or 0x01

        when (result) {
            0x11 -> {
                val loadInterceptor = LoadInterceptor(payChannelManager)
                payInterceptor = TargetPayInterceptor(loadInterceptor.loadPayInterceptors())
                payResultInterceptor = TargetPayResultInterceptor(loadInterceptor.loadPayResultInterceptors())
            }
            else -> {
                throw NullPointerException("interceptor is null")
            }
        }

        chainWrapper = ChainWrapper(
                payPayChannelManager = payChannelManager,
                orderManager = orderManager,
                payInterceptor = payInterceptor,
                payResultInterceptor = payResultInterceptor,
                dialogInterceptor = dialogInterceptor,
                orderInterceptor = orderInterceptor,
                lifeCycleManager = lifecycleManager,
                contextProvider = contextProvider,
                iPayResult = iPayResult)

        chainWrapper.callNext()
    }
}