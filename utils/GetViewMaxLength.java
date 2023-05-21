package com.example.chapter05.utils;

import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;

public class GetViewMaxLength {
    public static int getMaxLength(EditText et) {
        InputFilter[] filters = et.getFilters();
        for (InputFilter filter : filters) {
            if (filter instanceof InputFilter.LengthFilter) {
                int maxLength = ((InputFilter.LengthFilter) filter).getMax();
                return maxLength;
            }
        }
        return 0;
    }
}
