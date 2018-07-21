package com.devin.data.analysis.admin.core.exception;


import com.devin.data.analysis.admin.core.util.HttpStatus;

public abstract class BaseException extends RuntimeException {
    private int internalErrorCode;
    private String internalErrorMessage;
    private int errorCode;
    private String errorMessage;
    private Object data;
    private int httpCode;
    private String customInfo;

    public BaseException() {
    }

    public BaseException(int errorCode, String errorMessage, Throwable cause, Object data, int httpCode, String customInfo) {
        super(errorMessage, cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpCode = httpCode;
        this.data = data;
        this.internalErrorCode = errorCode;
        this.internalErrorMessage = errorMessage;
        this.customInfo = customInfo;
    }

    public BaseException(int errorCode, String errorMessage, Throwable cause, Object data, String customInfo) {
        super(errorMessage, cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.data = data;
        this.internalErrorCode = errorCode;
        this.internalErrorMessage = errorMessage;
        this.customInfo = customInfo;
    }

    public BaseException(int errorCode, String errorMessage, Throwable cause, Object data, int httpCode, int internalErrorCode, String internalErrorMessage, String customInfo) {
        super(errorMessage, cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpCode = httpCode;
        this.data = data;
        this.internalErrorCode = internalErrorCode;
        this.internalErrorMessage = internalErrorMessage;
        this.customInfo = customInfo;
    }

    public String getCustomInfo() {
        return this.customInfo;
    }

    public void setCustomInfo(String customInfo) {
        this.customInfo = customInfo;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getHttpCode() {
        return this.httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public int getInternalErrorCode() {
        return this.internalErrorCode;
    }

    public void setInternalErrorCode(int internalErrorCode) {
        this.internalErrorCode = internalErrorCode;
    }

    public String getInternalErrorMessage() {
        return this.internalErrorMessage;
    }

    public void setInternalErrorMessage(String internalErrorMessage) {
        this.internalErrorMessage = internalErrorMessage;
    }
}
