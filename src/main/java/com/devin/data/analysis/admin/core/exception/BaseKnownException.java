package com.devin.data.analysis.admin.core.exception;

import com.devin.data.analysis.admin.core.util.HttpStatus;

import java.text.MessageFormat;

public class BaseKnownException extends BaseException {
    public BaseKnownException() {
    }

    public BaseKnownException(int errorCode, String errorMessage, Throwable cause, Object data, int httpCode, String customInfo) {
        super(errorCode, errorMessage, cause, data, httpCode, customInfo);
    }

    public BaseKnownException(int errorCode, String errorMessage, Throwable cause, Object data, String customInfo) {
        super(errorCode, errorMessage, cause, data, customInfo);
    }

    public BaseKnownException(int errorCode, String errorMessage, Throwable cause, Object data, int httpCode, int internalErrorCode, String internalErrorMessage, String customInfo) {
        super(errorCode, errorMessage, cause, data, httpCode, internalErrorCode, internalErrorMessage, customInfo);
    }

    public BaseKnownException(int errorCode, String errorMessage) {
        this(errorCode, errorMessage, (Throwable)null, (Object)null, HttpStatus.INTERNAL_SERVER_ERROR.value(), errorCode, errorMessage, (String)null);
    }

    public BaseKnownException(int errorCode, String errorMessage, String customInfo) {
        this(errorCode, errorMessage, (Throwable)null, (Object)null, HttpStatus.INTERNAL_SERVER_ERROR.value(), errorCode, errorMessage, customInfo);
    }

    public static BaseKnownException getFormattedException(int errorCode, String errorMessage, Object... arguments) {
        return new BaseKnownException(errorCode, MessageFormat.format(errorMessage, arguments));
    }

    public static BaseKnownException getUserErrorException(int errorCode, String errorMessage) {
        return new BaseKnownException(errorCode, errorMessage, (Throwable)null, (Object)null, HttpStatus.INTERNAL_SERVER_ERROR.value(), errorCode, errorMessage, (String)null);
    }
}

