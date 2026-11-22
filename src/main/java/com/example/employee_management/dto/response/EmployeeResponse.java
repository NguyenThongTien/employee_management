package com.example.employee_management.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeResponse {
    private Long id;

    private String employeeCode;

    private String hireDate;

    // Thông tin account của employee
    private AccountDTO account;

    // Chỉ lấy thông tin cần thiết của Department
    private Long departmentId;

    private String departmentName;

    // Chỉ lấy thông tin cần thiết của Position
    private Long positionId;

    private String positionName;
}
