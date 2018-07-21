package com.devin.data.analysis.admin.file.exception;

import lombok.Data;

import java.io.Serializable;

@Data
public class ParseError implements Serializable {

    private static final long serialVersionUID = -494907271091884467L;

    private String desc;
    private String code = "0000";

    public ParseError() {
    }

    public ParseError(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public ParseError(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "ParseError{" +
                "desc='" + desc + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
