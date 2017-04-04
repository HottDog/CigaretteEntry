package com.wgy.cigaretteentry.util;

import android.content.Context;

/**
 * Created by 袁江超 on 2017/4/4.
 */

public class Util {
    /**
     *在Android的布局文件中，往往使用dp作为控件的宽度和高度尺寸，但是在Java代码中，调用getWidth()方法获得的尺寸单位
     *却是像素px,这两个单位有明显的区别：dp和屏幕的密度有关，而px与屏幕密度无关，所以使用时经常会涉及到两
     *者之间的互相转化
     */
    public static int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
    public static int Px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}
