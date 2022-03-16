package com.wenote.pay.dialog.interceptor

import com.wenote.pay.ContextProvider
import com.wenote.pay.chain.RealChain
import com.wenote.pay.channel.PayChannelManager
import com.wenote.pay.dialog.DialogManager
import com.wenote.pay.order.OrderManager


class DialogInterceptor(private var payChannelManager: PayChannelManager, private var orderManager: OrderManager) : IDialogInterceptor {
    private var dialogManager: DialogManager? = null

    override fun onResume() {
        dialogManager?.onResume()
    }

    override fun onDestroy() {
        dialogManager?.onDestroy()
    }

    override fun intercept(chain: RealChain) {
        tryCreateDialogManager(chain.contextProvider)
        dialogManager?.intercept(chain)
    }

    private fun tryCreateDialogManager(contextProvider:ContextProvider) {
        if (dialogManager != null) return
        dialogManager = DialogManager(contextProvider, payChannelManager, orderManager)

    }
}
