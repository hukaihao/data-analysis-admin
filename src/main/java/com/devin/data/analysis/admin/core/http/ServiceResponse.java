package com.devin.data.analysis.admin.core.http;

import java.io.Serializable;

public class ServiceResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean success = true;
    private String message = "请求成功";
    private Object data;

    public ServiceResponse() {
    }

    public ServiceResponse(boolean success) {
        this.success = success;
        this.message = "操作失败";
    }

    public ServiceResponse(Object data) {
        this.data = data;
    }

    public ServiceResponse(String message) {
        this.success = false;
        this.message = message;
    }

    public ServiceResponse(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    public ServiceResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ServiceResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.success = false;
        this.message = message == null ? "" : message.trim();
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String toString() {
        return "ServiceResponse [success=" + this.success + ", message=" + this.message + ", data=" + this.data + "]";
    }
}

