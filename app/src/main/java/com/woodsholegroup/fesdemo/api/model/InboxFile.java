package com.woodsholegroup.fesdemo.api.model;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.woodsholegroup.fesdemo.api.FESContract;

/**
 * A POJO class representing a file in the Inbox and an entry in the
 * {@link FESContract.Inbox} table
 */
public class InboxFile {

    private long id;
    private String uuid;
    private String filename;
    private String filePath;
    private long receptionDate;

    public InboxFile(long id, String uuid, String filename, String filePath, long receptionDate) {
        this.id = id;
        this.uuid = uuid;
        this.filename = filename;
        this.filePath = filePath;
        this.receptionDate = receptionDate;
    }

    public long getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFilename() {
        return filename;
    }

    public String getFilePath() {
        return filePath;
    }

    public long getReceptionDate() {
        return receptionDate;
    }

    /**
     * @param c a {@link Cursor} pointing to a row in the {@link FESContract.Inbox}
     * @return {@link InboxFile}
     */
    public static InboxFile fromCursor(@NonNull Cursor c) {
        return new InboxFile(c.getLong(c.getColumnIndex(FESContract.Inbox._ID)),
                c.getString(c.getColumnIndex(FESContract.Inbox.COLUMN_UUID)),
                c.getString(c.getColumnIndex(FESContract.Inbox.COLUMN_FILENAME)),
                c.getString(c.getColumnIndex(FESContract.Inbox.COLUMN_FILEPATH)),
                c.getLong(c.getColumnIndex(FESContract.Inbox.COLUMN_RECEPTION_DATE))
        );
    }
}
