package com.wenote.pay.pay.wxin

import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.wenote.pay.chain.RealChain
import com.wenote.pay.log.debug
import com.wenote.pay.order.PayResult
import com.wenote.pay.pay.IPayInterceptor
import com.yctapp.base.config.AppConstant
import com.yctapp.base.util.rxbus.RxBus
import org.json.JSONException
import org.json.JSONObject

class WXinPayInterceptor : IPayInterceptor {
    private var api:IWXAPI? = null
    private var result = WXPayResult()

    override fun intercept(chain: RealChain) {
        wxinPay(chain.orderManager.payInfo, chain)
    }

    private fun wxinPay(payInfo: String, chain: RealChain) {
        debug("准备发起微信支付")
        val context = chain.contextProvider.getActivity()?.get()

        context?.let {
            debug("准备发起微信支付*")
            if(api == null) {
                api =  WXAPIFactory.createWXAPI(context.applicationContext, AppConstant.WxPay.WX_APP_ID)
            }

            val isSuccess: Boolean = api!!.registerApp(com.yctapp.base.config.AppConstant.WxPay.WX_APP_ID)
            if (!isSuccess) {
                wxPayCallFalse(chain ,result, "微信支付初始化失败")
                return
            }

            val json: JSONObject
            try {
                json = JSONObject(payInfo)
            } catch (e: JSONException) {
                e.printStackTrace()
                return
            }

            val req = PayReq()
            try {
                req.appId = json.getString("appid")
                req.partnerId = json.getString("partnerid")
                req.prepayId = json.getString("prepayid")
                req.nonceStr = json.getString("noncestr")
                req.timeStamp = json.getString("timestamp")
                req.packageValue = json.getString("package")
                req.sign = json.getString("sign")

                if(api!!.sendReq(req)) {
                    // 微信支付调用成功
                    chain.callNext()
                    WXPayInfoHash.hash = chain.orderManager.status.payInfoHashCode
                }else {
                    // 微信支付调回用失败
                    wxPayCallFalse(chain, result,"微信支付调回用失败")
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                // 微信支付调回用失败
                wxPayCallFalse(chain, result, e.message)
            }
        }
    }

    private fun wxPayCallFalse(chain: RealChain, result: WXPayResult, reason:String? = null) {
        chain.callNext()

        result.payResult.code = -1
        result.payResult.result = PayResult.FALSE
        result.payResult.msg = ""
        reason?.let {
            result.payResult.msg = reason
        }
        result.payResult.payInfoHashCode = 0

        RxBus.getInstance().post(result)
    }

    override fun onResume() {

    }

    override fun onDestroy() {

    }
}