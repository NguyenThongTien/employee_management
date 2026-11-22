package com.example.employee_management.dto.resquest;

import com.example.employee_management.enums.LeaveStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateEmployeeLeaveRequest {
    Long employeeId;
    String startTime;
    String endTime;
    String reason;
}
