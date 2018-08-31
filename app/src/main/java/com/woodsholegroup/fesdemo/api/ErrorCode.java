package com.woodsholegroup.fesdemo.api;

import android.support.annotation.NonNull;

/**
 * Created by ltossou on 8/27/2018.
 */

public enum ErrorCode {

    BAD_INPUT_PARAMETER(400),
    FILE_ALREADY_EXISTS(401),
    NO_READ_ACCESS(403),
    FILE_NOT_FOUND(404),
    FILE_IS_DIRECTORY(406),
    FILE_TOO_LARGE(413),
    FILE_EMPTY(422),
    INTERNAL_ERROR(500);

    private final int code;

    ErrorCode(int code) {
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }

    @NonNull
    public static ErrorCode fromInt(int errorCode) {
        ErrorCode[] errorCodes = values();
        for (ErrorCode error : errorCodes) {
            if (error.getCode() == errorCode) {
                return error;
            }
        }
        throw new IllegalArgumentException("Unknown error code: " + errorCode);
    }
}
