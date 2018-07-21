package com.devin.data.analysis.admin.file.exception;

import java.util.List;

public class LogicValidationException extends Exception {
    private String errorCode;
    private String errorMessage;
    private List<ParseError> errorList;

    public LogicValidationException() {
    }

    public LogicValidationException(String errorCode, String errorMessage, List<ParseError> errorList, Exception e) {
        super(errorMessage, e);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorList = errorList;
    }

    public LogicValidationException(String errorMessage, List<ParseError> errorList, Exception e) {
        super(errorMessage, e);
        this.errorMessage = errorMessage;
        this.errorList = errorList;
    }

    public LogicValidationException(String errorCode, String errorMessage, Exception e) {
        super(errorMessage, e);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public LogicValidationException(List<ParseError> errorList, Exception e) {
        super(null, e);
        this.errorList = errorList;
    }

    public LogicValidationException(String errorMessage, Exception e) {
        super(errorMessage, e);
        this.errorMessage = errorMessage;
    }

    public LogicValidationException(String errorMessage) {
        super(errorMessage, null);
        this.errorMessage = errorMessage;
    }

    public LogicValidationException(List<ParseError> errorList) {
        super();
        this.errorList = errorList;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<ParseError> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<ParseError> errorList) {
        this.errorList = errorList;
    }
}
