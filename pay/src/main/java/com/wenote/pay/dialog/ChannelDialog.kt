package com.wenote.pay.dialog

import android.content.Context
import android.view.*
import android.widget.PopupWindow
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wenote.pay.ContextProvider
import com.wenote.pay.R
import com.wenote.pay.channel.Channel
import com.wenote.pay.channel.PayChannelManager
import com.wenote.pay.dialog.action.IDialogAction
import com.wenote.pay.dialog.action.IPayChannelAction
import com.wenote.pay.log.debug


internal class ChannelDialog(private val contextProvider: ContextProvider,
                             val payChannelManager: PayChannelManager,
                             val iPayChannelAction: IPayChannelAction) : IDialogAction {

    protected var popWnd: PopupWindow? = null
    private var recyclerView: RecyclerView? = null

    init {
        val activity = contextProvider.getActivity()?.get()
        activity?.run {
            val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.diialog_channel, null)

            view.findViewById<View>(R.id.closeLayout).setOnClickListener {
                onDialogClose()
                iPayChannelAction.payChannelDialogClose()
            }

            view.findViewById<View>(R.id.cancel).setOnClickListener {
                onDialogClose()
                iPayChannelAction.payChannelDialogClose()
            }

            recyclerView = view.findViewById(R.id.recycleView)
            recyclerView?.layoutManager = LinearLayoutManager(contextProvider.getActivity()?.get())
            recyclerView?.addItemDecoration(DividerItemDecoration(contextProvider.getActivity()?.get(), DividerItemDecoration.VERTICAL))
            val adapter = object : PayChannelAdapter() {
                override fun onItemClick(channel: Channel) {
                    debug("选择支付渠道，onItemClick ${channel.name}")
                    payChannelManager.resetChannel(channel)
                    notifyDataSetChanged()
                    onDialogClose()
                    iPayChannelAction.payChannelDialogClose()
                }
            }

            val channelList = ArrayList<Channel>()
            for (interceptor in payChannelManager.getPaymentChannel()) {
                channelList.add(interceptor.value)
            }
            adapter.setChannels(channelList)
            recyclerView?.adapter = adapter


            if (popWnd == null) {
                popWnd = PopupWindow(
                        view,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        true)
            }

            popWnd?.update()
            popWnd?.isFocusable = false
            popWnd?.setOutsideTouchable(false)
        }
    }

    override fun onDialogShow() {
        popWnd?.isShowing ?: return
        popWnd?.showAtLocation(contextProvider.getActivity()?.get()?.window?.decorView, Gravity.BOTTOM, 0, 0)
    }

    override fun onDialogClose() {
        popWnd?.dismiss()
    }
}