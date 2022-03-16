package com.wenote.alipay.utils;

import java.util.Map;

public interface AlipayCallback {
    void onReturn(Map<String, String> result);
}
