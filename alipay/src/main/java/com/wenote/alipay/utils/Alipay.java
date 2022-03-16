package com.wenote.alipay.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;

import java.util.LinkedHashMap;
import java.util.Map;

public class Alipay {
    private static final String APPID = "2018060760309856";
    private static final String PID = "2088221992775343";
    private static final String TARGET_ID = System.currentTimeMillis() + "";

    public static final String RSA2_PRIVATE = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDcP8pEjl2WuJvRmz3Tq/omDsiPicG5L1TSxqSpk7V06VKTMdkxwaPsp42BU7tzsRVXjlkKbGgh93px2VjsMfij7LnoP8xbrkE75AztySyXurRJbhouNizh8NZyIg9XyW8LXEYGnUuOoZRbZGYpbZRklX3+ym58oSM1KL0pCSSFCQZ1v4j8fhhubnkei3wyaEgPLnISKkJR+62LpYIt2832tRDw+vnWe6Dg/gJn4Zwlz4rude+wejy98n4NkAXWB5fKDf+RDZ440B8o7tyEBlApDM3fM3bENzYWE2JHj1AqUiEaaf2E9kvodaeKgo3m7PPwmQytdo1dRnzHrzx1bEgVAgMBAAECggEAO+kyORHMzWnsIMksuT5nriCJV9ouExSX6znTorkmO+YX6yg9H3bAA2jUaiB/tgmud4GmINq6PLsX2I45zQ4Ox3BmZZGtIic1YXg4OBpoAeHslqjXgEiGTvN3Mdan+8Y0aY+xEK2K1wKG25Fk5NIE8pNjkYI5KbefXx90zQvKnlED/LaWlBaXe43kNzHnsDpNrz6sGWSW2yw7xVTy1oWHUOzVC43hKR3G3+unIFiZCDEcDyMmqK++bqedLpKD3zsEbMswy5BOe/GtxpL0/Am3TkWe6avNWawhO1hO55TvscbLi6oU86K0TwK4mgltoCmJn13aCrwOLfvlu54YGD/y4QKBgQDuaB10EDSEVGBm7oq8Eg6ipkeNJ3OFvxDjuKtcEyjOZf4XwD25z1RGEHvwOekBdIYDBPhZ0VgFBJF4NaSMendFCF1kbJlmUC3SzDF0cBSdNIJiLwiz+rSUdvkuamgE4CDrQl1v84sMYWneqHs7Y3nbx/SmVtGTcFkO+fnLfKmWnQKBgQDsgKaAIHhXdNXEkbVRXSp5WD0qZAiZy/vDUz8wwLofkVguj9V3L4kekDIDEpcQaAxH56nuyuTw2cK7dGhff5+47rNMG28tYQrQ7q/T/sRfiWoUKIY4kCJclcKsYGiLBlGWigkC8+Rc/AYs580NpcwEHP09nmayd+ed+YnnudkB2QKBgQC2PW4uV2jdsuYSKMxyYVdAq9zMINkk5t4RRQvwkVDDHaHzI+fLTpo8CMPE6W+cxibezgDOH8Gp7EkK2+6R19euNGu3pXBJjobIuYAgYsDPPuFgDlYXxlAdoShve7VR7MYdMBjeyAuFhj14j8/LoQn4oEv6jykbDGl3kkyJaRz13QKBgQDOUNzvYWQaR15FASfANxpaiVOyq1mlcYHMP88cZMT5zP20gN2j/+AKvZgjfhAyy9IK2FoyauWWByIODdsPlHJuDi5z8mrml4WsBs10jwgYn/PeagvNRE/1nOAbKG+K+JQsU3bREJrjUyi/++eyRDTaUb/KBWlpv5hbA9n+QO7C4QKBgQDI/laB/64ucGP+v+RNOAiRirooAPteGPrVeFk6+um6k2KJPL1AefVp3ZwOXCpOhebHUxvL7BSNiFyafhTDJSfHbhVtpFq53WBdznkFK4jyydVVKYXI2lOwd20D6QXTn4cPQJf55ytg441fN9VVXVkDYG3LhR/qXPzWbxlHQ6uorQ==";
    public static final String RSA_PRIVATE = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3D/KRI5dlrib0Zs906v6Jg7Ij4nBuS9U0sakqZO1dOlSkzHZMcGj7KeNgVO7c7EVV45ZCmxoIfd6cdlY7DH4o+y56D/MW65BO+QM7cksl7q0SW4aLjYs4fDWciIPV8lvC1xGBp1LjqGUW2RmKW2UZJV9/spufKEjNSi9KQkkhQkGdb+I/H4Ybm55Hot8MmhIDy5yEipCUfuti6WCLdvN9rUQ8Pr51nug4P4CZ+GcJc+K7nXvsHo8vfJ+DZAF1geXyg3/kQ2eONAfKO7chAZQKQzN3zN2xDc2FhNiR49QKlIhGmn9hPZL6HWnioKN5uzz8JkMrXaNXUZ8x688dWxIFQIDAQAB";

    public static boolean checkAliPayInstalled(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }

    public static void authV2(View view, final Activity activity, final AlipayAuthCallback alipayAuthCallback) {
        if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
                || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
                || TextUtils.isEmpty(TARGET_ID)) {
            new AlertDialog.Builder(activity).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            dialoginterface.dismiss();
                        }
                    }).show();
            return;
        }

        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        LinkedHashMap<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);
        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
        final String authInfo = info + "&" + sign;

        Thread authThread = new Thread(new Runnable() {
            @Override
            public void run() {
                AuthTask authTask = new AuthTask(activity);
                Map<String, String> result = authTask.authV2(authInfo, true);
                alipayAuthCallback.getAlipayAuthData(result);
            }
        });
        authThread.start();
    }

    public static void payV2(final Activity activity, String payInfo, final AlipayCallback alipayCallback) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(activity).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            activity.finish();
                        }
                    }).show();
            return;
        }

        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2, payInfo);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                alipayCallback.onReturn(result);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
