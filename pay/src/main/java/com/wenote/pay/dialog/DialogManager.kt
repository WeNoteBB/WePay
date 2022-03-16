package com.wenote.pay.dialog

import android.content.Context
import android.view.WindowManager
import com.wenote.pay.ContextProvider
import com.wenote.pay.chain.RealChain
import com.wenote.pay.channel.PayChannelManager
import com.wenote.pay.dialog.action.IPayChannelAction
import com.wenote.pay.dialog.action.IPayInfoAction
import com.wenote.pay.interceptor.Interceptor
import com.wenote.pay.log.debug
import com.wenote.pay.order.OrderManager
import com.wenote.pay.order.PayResult


// 支付页面管理
internal class DialogManager(
        val contextProvider: ContextProvider,
        val payChannelManager: PayChannelManager,
        val orderManager: OrderManager) : IPayInfoAction, IPayChannelAction, Interceptor {

    private var context: Context? = null
    private var infoDialog: InfoDialog = InfoDialog(contextProvider, orderManager, payChannelManager, this)
    private var channelDialog: ChannelDialog = ChannelDialog(contextProvider, payChannelManager, this)
    private var mRealChain: RealChain? = null

    init {
        context = contextProvider.getActivity()?.get()?.applicationContext
    }

    @Volatile
    private var enableShow = true

    override fun intercept(chain: RealChain) {
        mRealChain = chain
        setBg(false)
        payInfoDialogOnShow()
    }

    override fun showSelPayChannelDialog() {
        if (!enableShow) {
            debug("生命周期结束，不允许重新打开支付渠道对话框")
            return
        }
        channelDialog.onDialogShow()
    }

    override fun startPay() {
        if (!enableShow) {
            debug("生命周期结束，不允许重新打开支付渠道对话框")
            return
        }
        debug("关闭对话框，调用下一个拦截链")
        closeAllDialog()
        mRealChain?.callNext()
    }

    override fun payInfoDialogCancel() {
        debug("关闭对话框，已取消支付")
        closeAllDialog()
        mRealChain?.run {
            orderManager.status.payInfoHashCode = 0
            orderManager.status.result = PayResult.FALSE
            orderManager.status.code= -1
            orderManager.status.msg = "已取消支付"
        }

        mRealChain?.callNext()
    }

    private fun payInfoDialogOnShow() {
        if (!enableShow) {
            debug("生命周期结束，不允许重新打开支付渠道对话框")
            return
        }
        debug("生命周期结束，不允许重新打开支付对话框")
        infoDialog.onDialogShow()
    }

    override fun payChannelDialogClose() {
        infoDialog.onDialogShow()
    }

    override fun onResume() {
        enableShow = true
    }

    override fun onDestroy() {
        enableShow = false
        closeAllDialog()
        mRealChain = null
    }

    private fun closeAllDialog() {
        setBg(true)
        infoDialog.onDialogClose()
        channelDialog.onDialogClose()
    }

    private fun setBg(alphaIs1: Boolean) {
        val context = contextProvider.getActivity()?.get()
        context?.run {
            val lp: WindowManager.LayoutParams = window.attributes
            lp.alpha = if (alphaIs1) {
                1.0f
            } else {
                0.4f
            }
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            window.attributes = lp
        }
    }
}