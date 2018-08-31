package com.woodsholegroup.fesdemo;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by ltossou on 8/15/2018.
 */

public class FESEvent {

    public static final int FILE_RECEPTION = 0;
    public static final int FILE_SENDING = 1;
    public static final int OTHER = 2;
    public static final int ERROR = 3;
    public static final int IRIDIUM_DATA = 4;
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm", Locale.ENGLISH);


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({FILE_RECEPTION, ERROR, OTHER, FILE_SENDING, IRIDIUM_DATA})
    public @interface EventType {
    }

    private String description;
    @EventType
    private int eventType;
    private long timestamp;

    public FESEvent(String description, @EventType int eventType) {
        this(description, eventType, System.currentTimeMillis());
    }

    public FESEvent(String description, @EventType int eventType, long timestamp) {
        this.description = description;
        this.eventType = eventType;
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    @EventType
    public int getEventType() {
        return eventType;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getFormattedTime() {
        return TIME_FORMAT.format(timestamp);
    }
}
