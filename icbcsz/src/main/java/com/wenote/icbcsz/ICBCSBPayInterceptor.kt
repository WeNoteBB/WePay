package com.wenote.pay.pay.icbc

import com.wenote.pay.chain.RealChain
import com.wenote.pay.log.debug
import com.wenote.pay.pay.IPayInterceptor
import com.yctapp.base.frame.pay.interceptor.icbc.ICBCSBFormActivity

class ICBCSBPayInterceptor(): IPayInterceptor {
    override fun intercept(chain: RealChain) {
        startPay(chain, chain.orderManager.payInfo)
    }

    private fun startPay(chain: RealChain, payInfo: String) {
        debug("工行数字人民币支付 支付信息 $payInfo")
        val activity = chain.contextProvider.getActivity()?.get()

        activity?.let {
            ICBCSBFormActivity.startForResult(activity, payInfo)
        }
    }

    override fun onResume() {
    }

    override fun onDestroy() {

    }
}