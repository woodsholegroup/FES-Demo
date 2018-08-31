package com.woodsholegroup.fesdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

/**
 * Created by ltossou on 8/15/2018.
 */

public class MonthSpinnerAdapter extends ArrayAdapter<String> {

    private static final String[] MONTHS = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    public MonthSpinnerAdapter(@NonNull Context context) {
        super(context, android.R.layout.simple_spinner_dropdown_item, MONTHS);
    }
}
