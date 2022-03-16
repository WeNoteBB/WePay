package com.wenote.pay.dialog

import android.content.Context
import android.view.*
import android.widget.PopupWindow
import android.widget.TextView
import com.wenote.pay.ContextProvider
import com.wenote.pay.R
import com.wenote.pay.channel.PayChannelManager
import com.wenote.pay.dialog.action.IDialogAction
import com.wenote.pay.dialog.action.IPayInfoAction
import com.wenote.pay.log.debug
import com.wenote.pay.order.OrderManager

// 确定支付Dialog
internal class InfoDialog(private val contextProvider: ContextProvider,
                          val orderManager: OrderManager,
                          private val payChannel: PayChannelManager,
                          private val payInfoAction: IPayInfoAction) : IDialogAction {

    private var popWnd: PopupWindow? = null
    private var closeView: View? = null
    private var payDescView: TextView? = null
    private var payChannelView: TextView? = null
    private var payImage: View? = null
    private var selectPayChannelView: View? = null

    init {
        val activity = contextProvider.getActivity()?.get()
        activity?.let {
            val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.dialog_payface, null)

            closeView = view.findViewById(R.id.closeLayout)
            payDescView = view.findViewById(R.id.payDesc)
            payChannelView = view.findViewById(R.id.channelName)
            payImage = view.findViewById(R.id.pay)
            selectPayChannelView = view.findViewById(R.id.selectChannel)

            closeView?.setOnClickListener {
                payInfoAction.payInfoDialogCancel()
                onDialogClose()
            }

            selectPayChannelView?.setOnClickListener {
                payInfoAction.showSelPayChannelDialog()
                onDialogClose()
            }

            payImage?.setOnClickListener { payInfoAction.startPay() }


            if (popWnd == null) {
                popWnd = PopupWindow(
                        view,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        true)
            }

            popWnd?.isFocusable = false
            popWnd?.setOutsideTouchable(false)
        }
    }

    override fun onDialogShow() {
        debug("打开支付详情页面")
        popWnd?.isShowing ?: return
        payChannelView?.text = payChannel.getDefaultChannel()?.name
        payDescView?.text = orderManager.payDesc
        popWnd?.showAtLocation(contextProvider.getActivity()?.get()?.window?.decorView, Gravity.BOTTOM, 0, 0)
    }

    override fun onDialogClose() {
        debug("支付详情页面关闭")
        popWnd?.dismiss()
    }
}