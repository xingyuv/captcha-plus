package com.example.verificationcodejavademo.utils;

import android.content.Context;

/**
 * Date:2020/5/19
 * author:wuyan
 */
public class DisplayUtil {


    public static int dip2px(Context context, Float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5);
    }

    public static int px2dip(Context context, Float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5);
    }
}
