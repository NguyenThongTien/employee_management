package com.example.employee_management.exceptions;

public enum ErrorCode {
    ACCOUNT_NOT_FOUND(1001,"Không tìm thấy tài khoản"),
    EMAIL_ALREADY_EXISTS(1002,"Email đã tồn tại"),
    PHONE_NUMBER_ALREADY_EXISTS(1003,"Số điện thoại đã tồn tại"),
    INVALID_PASSWORD(1004, "Mật khẩu không hợp lệ!"),
    DEPARTMENT_NOT_FOUND(1005,"Không tìm thấY phòng ban nào"),
    DEPARTMENT_NOT_EXISTS(1006,"Phòng ban không tồn tại"),
    DEPARTMENT_NOT_DELETE(1007,"Không thể xoá phòng ban khi nó còn tồn tại"),
    DEPARTMENT_ALREADY_EXISTS(1005,"Phòng ban đã tồn tại, bạn hãy active lại");

    private int code;
    private String message;

    ErrorCode(int code, String message) {
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
