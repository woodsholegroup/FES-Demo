package com.woodsholegroup.fesdemo;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ltossou on 7/3/2018.
 */

public class DatabaseListAdapter {

    private Uri contentUri;

    public DatabaseListAdapter(Uri contentUri) {
        this.contentUri = contentUri;
    }

    @NonNull
    public List<TableRow> getTableRows(@NonNull Context context) {
        List<TableRow> rows = new ArrayList<>();

        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(contentUri, null,
                    null, null, null);
            if (cursor != null) {
                rows = getTableRows(context, cursor);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return rows;
    }

    @NonNull
    private List<TableRow> getTableRows(Context context, Cursor cursor) {
        List<TableRow> rows = new ArrayList<>();

        // HEADER with column names
        TableRow header = new TableRow(context);
        for (int col = 0; col < cursor.getColumnCount(); col++) {
            TextView textView = new TextView(context);
            textView.setText(cursor.getColumnName(col));
            textView.setPadding(5, 5, 5, 5);
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            header.addView(textView);
        }
        rows.add(header);

        // ROWS
        boolean oddRow = false;
        while (cursor.moveToNext()) {
            TableRow row = new TableRow(context);
            for (int col = 0; col < cursor.getColumnCount(); col++) {
                TextView textView = new TextView(context);
                textView.setText(cursor.getString(col));
                textView.setPadding(5, 5, 5, 5);
                textView.setGravity(Gravity.CENTER);

                if (oddRow) {
                    textView.setBackgroundColor(Color.parseColor("#F2F2F2"));
                }

                row.addView(textView);
            }
            oddRow = !oddRow;

            rows.add(row);
        }

        return rows;
    }
}
