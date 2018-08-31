package com.woodsholegroup.fesdemo.api.model;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.woodsholegroup.fesdemo.api.FESContract;

/**
 * A POJO class representing a file in the Outbox and an entry in the
 * {@link com.woodsholegroup.fesdemo.api.FESContract.Outbox} table
 */
public class OutboxFile {

    private long id;
    private String uuid;
    private long cmid;
    private String filePath;
    private int status;
    private String errorMsg;
    private long transmissionDate;
    private long creationDate;

    public OutboxFile() {

    }

    public OutboxFile(long id, String uuid, long cmid, String fileName, int status, String errorMsg,
                      long transmissionDate, long creationDate) {
        this.id = id;
        this.uuid = uuid;
        this.cmid = cmid;
        this.filePath = fileName;
        this.status = status;
        this.errorMsg = errorMsg;
        this.transmissionDate = transmissionDate;
        this.creationDate = creationDate;
    }

    public long getId() {
        return id;
    }

    public String getUUID() {
        return uuid;
    }

    public long getCmid() {
        return cmid;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getStatus() {
        return status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    // it's preferred to not overwrite this field so append instead
    public void appendToErrorMsg(String errorMsg) {
        if (this.errorMsg == null) {
            this.errorMsg = errorMsg;
        } else {
            this.errorMsg = this.errorMsg + " " + errorMsg;
        }
    }

    public long getTransmissionDate() {
        return transmissionDate;
    }

    public long getCreationDate() {
        return creationDate;
    }

    /**
     * @param c a {@link Cursor} pointing to a row in the {@link com.woodsholegroup.fesdemo.api.FESContract.Outbox}
     * @return {@link OutboxFile}
     */
    public static OutboxFile fromCursor(@NonNull Cursor c) {
        return new OutboxFile(c.getLong(c.getColumnIndex(FESContract.Outbox._ID)),
                c.getString(c.getColumnIndex(FESContract.Outbox.COLUMN_UUID)),
                c.getLong(c.getColumnIndex(FESContract.Outbox.COLUMN_CMID)),
                c.getString(c.getColumnIndex(FESContract.Outbox.COLUMN_FILEPATH)),
                c.getInt(c.getColumnIndex(FESContract.Outbox.COLUMN_STATUS)),
                c.getString(c.getColumnIndex(FESContract.Outbox.COLUMN_ERROR_MSG)),
                c.getLong(c.getColumnIndex(FESContract.Outbox.COLUMN_TRANSMISSION_DATE)),
                c.getLong(c.getColumnIndex(FESContract.Outbox.COLUMN_CREATION_DATE))
        );
    }
}
