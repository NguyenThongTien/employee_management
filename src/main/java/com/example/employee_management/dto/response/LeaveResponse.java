package com.example.employee_management.dto.response;

import com.example.employee_management.enums.LeaveStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LeaveResponse {
    private Long id;

    private String startTime;

    private String endTime;

    private String reason;

    private LeaveStatus status;

    private String createdAt;

    private String approvedAt;

    private String rejectReason;

    // Thông tin employee
    private Long employeeId;

    private String employeeCode;

    private String employeeName;
}
