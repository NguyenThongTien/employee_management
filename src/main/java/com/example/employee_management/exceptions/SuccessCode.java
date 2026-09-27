package com.example.employee_management.exceptions;

public enum SuccessCode {
    Success(1000, "Thành công");

    private int code;
    private String message;

    SuccessCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
