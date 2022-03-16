package com.wenote.pay.dialog.action

// 支付Dialog行为接口
internal interface IPayInfoAction {
    fun showSelPayChannelDialog()
    fun startPay()
    //fun payInfoDialogDefaultAcctRecharge()
    fun payInfoDialogCancel()
   // fun payInfoDialogOnShow()
}