package com.woodsholegroup.fesdemo.api;

/**
 * This class contains the FES intents that a client application can broadcast to invoke
 * various FES actions.
 * See Android documentation for information about how to use Intents:
 * <a href="https://developer.android.com/guide/components/intents-filters.html" target="_blank>
 * Intents and Intent Filters</a>
 */
public class Intents {

    private Intents() {}

    /**
     * Registers your application to the FES module
     * <p>
     * Extras passed:
     * <ul>
     * <li>{@link #EXTRA_PACKAGE_NAME} REQUIRED</li>
     * </ul>
     * <p>
     */
    public static final String ACTION_REGISTER = "com.clsa.thoriumxfes.action.REGISTER";

    /**
     * Start the scan of the outbox folder for new files to be sent via satellite
     * <p>
     * Extras passed:
     * <ul>
     * <li>NONE</li>
     * </ul>
     * <p>
     */
    public static final String ACTION_START_SCAN = "com.clsa.thoriumxfes.action.START_SCAN";
    public static final String ACTION_DELETE_FILE_REF = "com.clsa.thoriumxfes.action.DELETE_FILE";

    /**
     * Retrieves info about the last known Iridium position (latitude, longitude, GPS date,
     * status of data download/reception)
     * <p>
     * Extras passed:
     * <ul>
     * <li>NONE</li>
     * </ul>
     * <p>
     */
    public static final String ACTION_BROADCAST_IRIDIUM_STATUS = "com.clsa.thoriumxfes.action.GET_IRIDIUM_STATUS";

    /**
     * Intent extra used with {@link #ACTION_REGISTER} to specify the name of your application
     * package
     * <p>
     * Type: STRING
     */
    public static final String EXTRA_PACKAGE_NAME = "com.clsa.thoriumxfes.extra.PACKAGE_NAME";
}
