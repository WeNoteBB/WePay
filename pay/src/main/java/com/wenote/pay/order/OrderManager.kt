package com.wenote.pay.order


/**
 * 支付订单信息类
 * payFee 支付金额， 单位分
 * data： 支付订单详细信息
 */
data class  OrderManager(
        var payFee: Int = -1,
        var payDesc: String = "",
        var orderId:String = "",
        var detail:HashMap<String, String> = HashMap(),
        var payInfo: String = "",
        var status: OrderStatus = OrderStatus(-1, "", PayResult.SUCCESS),
)

data class OrderStatus(var code: Int,
                       var msg: String,
                       var result: PayResult,
                       var channelCode: String = "",
                       var payInfoHashCode: Int = -1)

enum class PayResult { SUCCESS, FALSE }

