package com.example.chapter05.utils;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;


public class HindTextWatcher implements TextWatcher {
    private EditText et;
    private int maxLength;
    private Activity act;

    public HindTextWatcher(Activity act, EditText et, int maxLength) {
        this.maxLength = maxLength;
        this.et = et;
        this.act = act;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String str = s.toString();
        if (str.length() == maxLength) {
            ViewUtil.hideInputMethod(act, et);
        }
    }
}
