package com.woodsholegroup.fesdemo.util;

import android.support.annotation.NonNull;

import com.woodsholegroup.fesdemo.api.FESManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by ltossou on 8/28/2018.
 */

public class FileManager {

    @NonNull
    public static File createFileInOutbox(String filename, String content) throws IOException {
        final File file = new File(FESManager.Folders.OUTBOX, filename);
        file.createNewFile();
        FileOutputStream fOut = new FileOutputStream(file);
        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
        myOutWriter.append(content);

        myOutWriter.close();

        fOut.flush();
        fOut.close();

        return file;
    }

    @NonNull
    public static File createFile(@NonNull String filename, @NonNull String content) throws IOException {
        File fileToSend = new File("path");
        if (!content.isEmpty()) {
            fileToSend = createFileInOutbox(filename, content);
        }
        return fileToSend;
    }

    @NonNull
    public static String generateFileName() {
        return "fes_test_file_" + System.currentTimeMillis() + ".msg";
    }
}
