package com.wenote.pay.pay.apay

import com.wenote.pay.chain.RealChain
import com.wenote.pay.order.PayResult
import com.wenote.pay.pay.IPayInterceptor
import com.wenote.alipay.utils.Alipay
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.net.URLDecoder

class APayInterceptor : IPayInterceptor {
    private val SUCCESS = "9000"
    private val resultKey = "resultStatus"

    override fun intercept(chain: RealChain) {
        try {
            val pay_info = URLDecoder.decode(chain.orderManager.payInfo, "utf-8")

            Alipay.payV2(chain.contextProvider.getActivity()?.get(), pay_info) { result ->
                try {
                    val jsonObject = JSONObject()
                    val iter = result.keys.iterator()
                    while (iter.hasNext()) {
                        val key = iter.next()
                        val value = result[key]
                        jsonObject.put(key, value)
                    }
                    if (SUCCESS == jsonObject.getString(resultKey)) {
                        chain.orderManager.status.msg = "支付成功"
                        chain.orderManager.status.code = 0
                        chain.orderManager.status.result = PayResult.SUCCESS
                        APayResult.hash = chain.orderManager.status.payInfoHashCode
                    } else {
                        chain.orderManager.status.msg = "支付失败"
                        chain.orderManager.status.code = -1
                        chain.orderManager.status.result = PayResult.FALSE
                        APayResult.hash = 0
                    }

                    chain.callNext()

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
    }

    override fun onResume() {

    }

    override fun onDestroy() {

    }

}