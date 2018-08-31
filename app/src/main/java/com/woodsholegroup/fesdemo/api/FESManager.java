package com.woodsholegroup.fesdemo.api;

import android.os.Environment;

import java.io.File;

/**
 * Created by ltossou on 7/2/2018.
 */

public class FESManager {

    public static final class Folders {
        private static final String FES_ROOT_DIRECTORY =
                Environment.getExternalStorageDirectory() + File.separator + "thorium" + File.separator + "FES";

        /**
         * Folder on the primary external storage directory that contains all the received files
         */
        public static final String INBOX = FES_ROOT_DIRECTORY + File.separator + "Inbox";

        /**
         * Folder on the primary external storage directory that should contain all the files to be sent
         */
        public static final String OUTBOX = FES_ROOT_DIRECTORY + File.separator + "Outbox";
    }

    public static final class Permission {
        private static final String FES_BROADCASTS = "com.clsa.thoriumx.broadcasts";
        private static final String READ_FES_PROVIDER = "com.clsa.thoriumxfes.provider.PERMISSION_READ";
    }
}