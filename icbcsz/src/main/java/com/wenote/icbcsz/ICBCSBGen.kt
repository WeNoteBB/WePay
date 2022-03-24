package com.yctapp.base.frame.pay.interceptor.icbc

import com.wenote.pay.channel.Channel
import com.wenote.pay.channel.ChannelType
import com.wenote.pay.pay.icbc.ICBCSBPayInterceptor
import com.wenote.pay.pay.icbc.ICBCSBResultInterceptor
import com.yctapp.base.R

object ICBCSBGen {
    fun gen(): Channel {
        val ICBCSB = Channel(
                code = "ICBCSB",
                name = "工行数字人民币支付",
                payInterceptor = ICBCSBPayInterceptor(),
                payResultInterceptor = ICBCSBResultInterceptor(),
                channelType = ChannelType.OTHERS,
                icon = R.mipmap.check_icon)
        return ICBCSB
    }
}