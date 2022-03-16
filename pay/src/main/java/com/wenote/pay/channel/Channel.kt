package com.wenote.pay.channel

import com.wenote.pay.pay.IPayInterceptor
import com.wenote.pay.pay.PayResultInterceptor

/**
 * 支付渠道
 * code 渠道代码
 * name：支付渠道中文名称
 */
data class Channel(val code: String,
                   val name: String,
                   var icon:Int,
                   var payInterceptor: IPayInterceptor,
                   var payResultInterceptor:PayResultInterceptor,
                   var channelType: ChannelType = ChannelType.OTHERS,
                   var channelStatus: ChannelStatus = ChannelStatus.DISABLE)

enum class ChannelType { DEFAULT, OTHERS }
enum class ChannelStatus { ENABLE, DISABLE }

