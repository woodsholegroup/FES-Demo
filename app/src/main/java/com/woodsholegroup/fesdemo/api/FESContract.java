package com.woodsholegroup.fesdemo.api;

import android.net.Uri;
import android.os.Environment;
import android.provider.BaseColumns;

import java.io.File;

/**
 * The contract between the FES Content provider and client applications. Contains definitions for
 * the supported URIs and columns.
 * <p>
 * The provider is <b>READ ONLY</b>, i.e only {@link android.content.ContentProvider#query} is allowed
 * and you must hold the {@link com.woodsholegroup.fesdemo.api.FESManager.Permission#READ_FES_PROVIDER}
 * permission. Not holding this required permission or using a non allowed method (insert, update, delete) results in a
 * {@link SecurityException} being thrown from the call
 */
public class FESContract {

    /** The authority for the FES provider */
    private static final String CONTENT_AUTHORITY = "com.clsa.thoriumxfes.provider_public";

    /** A content:// style uri to the authority for the contacts provider */
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String BASE_FES_FOLDER = Environment.getExternalStorageDirectory() + "thorium/FES";

    public static final class Outbox implements BaseColumns {
        /** This utility class cannot be instantiated */
        private Outbox() {
        }

        /**
         * Path of the directory that contains the files that have to be sent via satellite
         */
        public static final String CONTENT_DIRECTORY = BASE_FES_FOLDER + File.separator + "Outbox";

        public static final String TABLE_NAME = "Outbox";
        public static final String COLUMN_UUID = "UUID";
        public static final String COLUMN_CMID = "CMID";
        public static final String COLUMN_STATUS = "Status";
        public static final String COLUMN_FILEPATH = "File_Path";
        public static final String COLUMN_ERROR_MSG = "Error_Msg";
        public static final String COLUMN_TRANSMISSION_DATE = "Transmission_Date";
        public static final String COLUMN_CREATION_DATE = "Creation_Date";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();
    }

    public static class Inbox implements BaseColumns {
        /** This utility class cannot be instantiated */
        private Inbox() {
        }

        /**
         * Path of the directory that contains the files that have been received
         */
        public static final String CONTENT_DIRECTORY = BASE_FES_FOLDER + File.separator + "Inbox";

        public static final String TABLE_NAME = "Inbox";
        public static final String COLUMN_UUID = "UUID";
        public static final String COLUMN_FILENAME = "File_Name";
        public static final String COLUMN_FILEPATH = "File_Path";
        public static final String COLUMN_RECEPTION_DATE = "Date_Received";

        /**
         * The content:// style URI for this table
         */
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();
    }

    public static class Status implements BaseColumns {
        /** This utility class cannot be instantiated */
        private Status() {
        }

        public static final String TABLE_NAME = "Status";
        public static final String COLUMN_LATITUDE = "Latitude";
        public static final String COLUMN_LONGITUDE = "Longitude";
        public static final String COLUMN_IRIDIUM_STATUS = "Iridium_Status";
        public static final String COLUMN_RSSI = "RSSI";
        public static final String COLUMN_INBOUND_DATA = "Inbound_Data";
        public static final String COLUMN_OUTBOUND_DATA = "Outbound_Data";
        public static final String COLUMN_CREATION_DATE = "Creation_Date";

        /**
         * The content:// style URI for the {@link Status} table
         */
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();
    }

    /**
     * category - the relevant BROADCAST_CATEGORY_* which is either one of the FES tables or general
     * entry id - the db id of the table name this broadcast is relevant for as determined by category
     * type - the type of broadcast
     * date sent - the unix timestamp when the broadcast was sent
     */
    public static class BroadcastLogs implements BaseColumns {
        /** This utility class cannot be instantiated */
        private BroadcastLogs() {
        }

        public static final String TABLE_NAME = "BroadcastLogs";
        public static final String COLUMN_CATEGORY = "Category";
        public static final String COLUMN_ENTRY_ID = "Entry_ID";
        public static final String COLUMN_TYPE = "Type";
        public static final String COLUMN_DATE_SENT = "Date_Sent";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();
    }
}
