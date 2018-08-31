package com.woodsholegroup.fesdemo;

import android.content.ContentResolver;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.woodsholegroup.fesdemo.api.FESContract;
import com.woodsholegroup.fesdemo.api.model.IridiumStatus;
import com.woodsholegroup.fesdemo.api.model.OutboxFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ltossou on 8/28/2018.
 */

public class FESDatabase {

    private final ContentResolver contentResolver;

    public FESDatabase(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    @Nullable
    public IridiumStatus getStatusAtDate(Date date) {
        Cursor cursor = null;
        try {
            // Find the entry that has the closest creation_date
            cursor = contentResolver.query(FESContract.Status.CONTENT_URI,
                    null, null, null,
                    "ABS(" + FESContract.Status.COLUMN_CREATION_DATE + " - " + date.getTime() + ") ASC LIMIT 1");
            if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
                return IridiumStatus.fromCursor(cursor);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return null;
    }

    @NonNull
    public List<OutboxFile> getPendingFiles() {
        List<OutboxFile> files = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(FESContract.Outbox.CONTENT_URI,
                    null,
                    null,
                    null, null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    files.add(OutboxFile.fromCursor(cursor));
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return files;
    }
}
