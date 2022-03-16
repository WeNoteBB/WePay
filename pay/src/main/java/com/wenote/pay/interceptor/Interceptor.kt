package com.wenote.pay.interceptor

import com.wenote.pay.chain.RealChain
import com.wenote.pay.lifecycle.Lifecycle

interface Interceptor : Lifecycle {
    fun intercept(chain: RealChain)
}