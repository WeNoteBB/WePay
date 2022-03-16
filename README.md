# WePay
支付封装

## 调用方法

WPay.auto().with(this).withPayFee(amount).withPayInfo(orderDesc)
                        .addChannel(ICBCSBGen.gen())
                        .addInterceptor(CardTransferInterceptor(req))
                        .call(object : IPayResult {
                            override fun getOrderResult(orderStatus: OrderStatus) {
                                debug("完成支付******")
                                opResult(orderStatus.result)
                            }
                        })