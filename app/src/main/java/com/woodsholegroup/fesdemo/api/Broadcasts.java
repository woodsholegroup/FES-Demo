package com.woodsholegroup.fesdemo.api;

/**
 * This class contains the Broadcast intents that are fired by the FES module.
 * You must hold the {@link com.woodsholegroup.fesdemo.api.FESManager.Permission#FES_BROADCASTS} permission
 * in order to receive these broadcasts.
 * <p>
 * See Android documentation for information about how to use Intents:
 * <a href="https://developer.android.com/guide/components/intents-filters.html" target="_blank>
 * Intents and Intent Filters</a>
 */
public class Broadcasts {

    private Broadcasts() {
    }

    /**
     * Broadcast sent when the scan of the Outbox and Inbox starts
     * <p>
     * Extras passed: NONE
     */
    public static final String ACTION_SCAN_STARTED = "com.clsa.thoriumxfes.action.SCAN_STARTED";

    /**
     * Broadcast sent when the scan of the Outbox and Inbox finishes
     * <p>
     * Extras passed:
     * <ul>
     * <li>{@link #EXTRA_FILENAME}</li>
     * </ul>
     */
    public static final String ACTION_SCAN_FINISHED = "com.clsa.thoriumxfes.action.SCAN_FINISHED";

    /**
     * A broadcast action which is sent when any change has been made to the Outbox, such
     * as ...
     * <p>
     * Extras passed:
     * <ul>
     * <li>{@link #EXTRA_UUID}</li>
     * <li>{@link #EXTRA_FILENAME}</li>
     * <li>{@link #EXTRA_STATUS}</li>
     * </ul>
     */
    public static final String ACTION_STATUS_CHANGED = "com.clsa.thoriumxfes.action.STATUS_CHANGED";

    /**
     * Extras passed:
     * <ul>
     * <li>{@link #EXTRA_UUID}</li>
     * <li>{@link #EXTRA_FILENAME}</li>
     * </ul>
     */
    public static final String ACTION_FILE_RECEIVED = "com.clsa.thoriumxfes.action.FILE_RECEIVED";
    public static final String ACTION_ERROR = "com.clsa.thoriumxfes.action.ERROR";

    /**
     * Extras passed:
     * <ul>
     * <li>{@link #EXTRA_IRIDIUM_STATUS}</li>
     * <li>{@link #EXTRA_LATITUDE}</li>
     * <li>{@link #EXTRA_LONGITUDE}</li>
     * <li>{@link #EXTRA_RSSI}</li>
     * <li>{@link #EXTRA_INCOMING_DATA}</li>
     * <li>{@link #EXTRA_OUTGOING_DATA}</li>
     * <li>{@link #EXTRA_TIMESTAMP}</li>
     * </ul>
     */
    public static final String ACTION_IRIDIUM_STATUS = "com.clsa.thoriumxfes.action.IRIDIUM_STATUS";


    /**
     * Intent extra used with {@link #ACTION_FILE_RECEIVED} and {@link #ACTION_STATUS_CHANGED}
     * to specify the UUID of the received or updated file
     * <p>
     * Type: STRING
     */
    public static final String EXTRA_UUID = "com.clsa.thoriumxfes.extra.UUID";

    /**
     * Intent extra used with {@link #ACTION_FILE_RECEIVED} and {@link #ACTION_STATUS_CHANGED}
     * to specify the name of the received or updated file
     * <p>
     * Type: STRING
     */
    public static final String EXTRA_FILENAME = "com.clsa.thoriumxfes.extra.FILE_NAME";

    /**
     * Intent extra used with {@link #ACTION_IRIDIUM_STATUS} to specify the latitude of the
     * last received Iridium position
     * <p>
     * Type: DOUBLE
     */
    public static final String EXTRA_LATITUDE = "com.clsa.thoriumxfes.extra.LATITUDE";

    /**
     * Intent extra used with {@link #ACTION_IRIDIUM_STATUS} to specify the longitude of the
     * last received Iridium position
     * <p>
     * Type: DOUBLE
     */
    public static final String EXTRA_LONGITUDE = "com.clsa.thoriumxfes.extra.LONGITUDE";

    /**
     * Intent extra used with {@link #ACTION_STATUS_CHANGED} to specify the updated
     * transmission status of the specified outgoing file
     * <p>
     * Possible values are:
     * <ul>
     * <li>{@link #STATUS_PENDING}</li>
     * <li>{@link #STATUS_TRANSFERRING}</li>
     * <li>{@link #STATUS_COMPLETED}</li>
     * <li>{@link #STATUS_FAILED}</li>
     * <li>{@link #STATUS_CANCELLED}</li>
     * <li>{@link #STATUS_UNKNOWN}</li>
     * </ul>
     * <p>
     * Type: STRING
     */
    public static final String EXTRA_STATUS = "com.clsa.thoriumxfes.extra.STATUS";

    /**
     * Possible values are:
     * <ul>
     * <li>{@link com.woodsholegroup.fesdemo.api.model.IridiumStatus#STATE_CONNECTED}</li>
     * <li>{@link com.woodsholegroup.fesdemo.api.model.IridiumStatus#STATE_DISCONNECTED}</li>
     * <li>{@link com.woodsholegroup.fesdemo.api.model.IridiumStatus#STATE_ACQUISITION_IN_PROGRESS}</li>
     * <li>{@link com.woodsholegroup.fesdemo.api.model.IridiumStatus#STATE_UNKNOWN}</li>
     * </ul>
     * <p>
     * Type: INT
     */
    public static final String EXTRA_IRIDIUM_STATUS = "com.clsa.thoriumxfes.extra.IRIDIUM_STATUS";

    /**
     * Intent extra used with {@link #ACTION_IRIDIUM_STATUS} to specify
     * <p>
     * Type: INT
     */
    public static final String EXTRA_RSSI = "com.clsa.thoriumxfes.extra.RSSI";

    /**
     * Intent extra used with {@link #ACTION_IRIDIUM_STATUS} to specify if the beacon is
     * currently downloading data
     * <p>
     * Type: BOOLEAN
     */
    public static final String EXTRA_INCOMING_DATA = "com.clsa.thoriumxfes.extra.INCOMING_DATA";

    /**
     * Intent extra used with {@link #ACTION_IRIDIUM_STATUS} to specify if the beacon is
     * currently transmitting data
     * <p>
     * Type: BOOLEAN
     */
    public static final String EXTRA_OUTGOING_DATA = "com.clsa.thoriumxfes.extra.OUTGOING_DATA";

    /**
     * Intent extra used with {@link #ACTION_IRIDIUM_STATUS} to specify
     * <p>
     * Type: LONG
     */
    public static final String EXTRA_TIMESTAMP = "com.clsa.thoriumxfes.extra.TIMESTAMP";

    /**
     * Intent extra used with {@link #ACTION_ERROR} to specify the type of error raised
     * <p>
     * Possible values are:
     * <ul>
     * <li>{@link ErrorCode#BAD_INPUT_PARAMETER}</li>
     * <li>{@link ErrorCode#FILE_ALREADY_EXISTS}</li>
     * <li>{@link ErrorCode#FILE_NOT_FOUND}</li>
     * <li>{@link ErrorCode#FILE_TOO_LARGE}</li>
     * <li>{@link ErrorCode#NO_READ_ACCESS}</li>
     * <li>{@link ErrorCode#FILE_IS_DIRECTORY}</li>
     * </ul>
     * <p>
     * Type: INT
     */
    public static final String EXTRA_ERROR_CODE = "com.clsa.thoriumxfes.extra.ERROR_CODE";

    /**
     * Intent extra used with {@link #ACTION_ERROR} to specify a human-readable message that provides
     * more details about the error
     * <p>
     * Type: TEXT
     */
    public static final String EXTRA_MESSAGE = "com.clsa.thoriumxfes.extra.MESSAGE";

    /**
     * Value of {@link FESContract.Outbox#COLUMN_STATUS} and for {@link #EXTRA_STATUS}
     * when the sending is waiting to start.
     */
    public static final String STATUS_PENDING = "PENDING";

    /**
     * Value of {@link FESContract.Outbox#COLUMN_STATUS} and for {@link #EXTRA_STATUS}
     * when the sending has successfully completed.
     */
    public static final String STATUS_COMPLETED = "COMPLETED";

    /**
     * Value of {@link FESContract.Outbox#COLUMN_STATUS} and for {@link #EXTRA_STATUS}
     * when the sending has failed (and will not be retried).
     */
    public static final String STATUS_FAILED = "FAILED";

    /**
     * Value of {@link FESContract.Outbox#COLUMN_STATUS} and for {@link #EXTRA_STATUS}
     * when the sending is currently in progress.
     */
    public static final String STATUS_TRANSFERRING = "TRANSFERRING";

    public static final String STATUS_CANCELLED = "CANCELLED";
    public static final String STATUS_UNKNOWN = "UNKNOWN";
}
