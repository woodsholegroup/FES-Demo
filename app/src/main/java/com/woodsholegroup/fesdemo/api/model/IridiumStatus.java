package com.woodsholegroup.fesdemo.api.model;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import com.woodsholegroup.fesdemo.api.Broadcasts;
import com.woodsholegroup.fesdemo.api.FESContract;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A POJO class representing a status of the Iridium connection and an entry in the
 * {@link com.woodsholegroup.fesdemo.api.FESContract.Status} table
 */
public class IridiumStatus {

    public static final int STATE_CONNECTED = 1;
    public static final int STATE_DISCONNECTED = 2;
    public static final int STATE_ACQUISITION_IN_PROGRESS = 3;
    public static final int STATE_UNKNOWN = 4;

    @IntDef({STATE_CONNECTED, STATE_DISCONNECTED, STATE_ACQUISITION_IN_PROGRESS, STATE_UNKNOWN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {
    }

    private long id;
    private long creationDate;
    private double latitude = 0d;
    private double longitude = 0d;
    @Status
    private int iridiumStatus = STATE_UNKNOWN;
    private int rssi;
    private boolean inboundData;
    private boolean outboundData;

    public IridiumStatus(long creationDate, double latitude, double longitude,
                         int iridiumStatus, Integer rssi, Boolean inboundData, Boolean outboundData) {
        this.creationDate = creationDate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.iridiumStatus = iridiumStatus;
        this.rssi = rssi;
        this.inboundData = inboundData;
        this.outboundData = outboundData;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Status
    public int getIridiumStatus() {
        return iridiumStatus;
    }

    public Integer getRssi() {
        return rssi;
    }

    public Boolean getInboundData() {
        return inboundData;
    }

    public Boolean getOutboundData() {
        return outboundData;
    }

    /**
     * @param c a {@link Cursor} pointing to a row in the {@link com.woodsholegroup.fesdemo.api.FESContract.Status}
     * @return {@link IridiumStatus}
     */
    public static IridiumStatus fromCursor(Cursor c) {
        return new IridiumStatus(c.getLong(c.getColumnIndex(FESContract.Status.COLUMN_CREATION_DATE)),
                c.getDouble(c.getColumnIndex(FESContract.Status.COLUMN_LATITUDE)),
                c.getDouble(c.getColumnIndex(FESContract.Status.COLUMN_LONGITUDE)),
                c.getInt(c.getColumnIndex(FESContract.Status.COLUMN_IRIDIUM_STATUS)),
                c.getInt(c.getColumnIndex(FESContract.Status.COLUMN_RSSI)),
                c.getInt(c.getColumnIndex(FESContract.Status.COLUMN_INBOUND_DATA)) == 1,
                c.getInt(c.getColumnIndex(FESContract.Status.COLUMN_OUTBOUND_DATA)) == 1);
    }

    @NonNull
    public static IridiumStatus fromIntent(@NonNull Intent intent) {
        int status = intent.getIntExtra(Broadcasts.EXTRA_IRIDIUM_STATUS, -1);
        double latitude = intent.getDoubleExtra(Broadcasts.EXTRA_LATITUDE, 0);
        double longitude = intent.getDoubleExtra(Broadcasts.EXTRA_LONGITUDE, 0);
        int rssi = intent.getIntExtra(Broadcasts.EXTRA_RSSI, -1);
        boolean incomingData = intent.getBooleanExtra(Broadcasts.EXTRA_INCOMING_DATA, false);
        boolean outgoingData = intent.getBooleanExtra(Broadcasts.EXTRA_OUTGOING_DATA, false);
        long timestamp = intent.getLongExtra(Broadcasts.EXTRA_TIMESTAMP, -1L);

        return new IridiumStatus(timestamp, latitude, longitude, status, rssi, incomingData,
                outgoingData);
    }

    @Override
    public String toString() {
        return "IridiumStatus {" +
                " creationDate=" + creationDate +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", iridiumStatus=" + iridiumStatus +
                ", rssi=" + rssi +
                ", inboundData=" + inboundData +
                ", outboundData=" + outboundData +
                '}';
    }
}
