package com.example.chapter05.utils;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by ding on 2023/5/20
 * 该工具类提供隐藏输入法的方法
 * author:ding
 */
public class ViewUtil {
    public static void hideInputMethod(Activity act, View v) {
//        获取输入法管理器
        InputMethodManager imm = (InputMethodManager) act.getSystemService(Activity.INPUT_METHOD_SERVICE);
//        隐藏输入法
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
