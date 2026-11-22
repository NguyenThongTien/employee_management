package com.example.employee_management.dto.response;

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
public class CreateEmployeeLeaveResponse {
    String startTime;
    String endTime;
    String reason;
    @Enumerated(EnumType.STRING) // Nó bảo JPA lưu enum dưới dạng String trong database
    LeaveStatus status;
    String createdAt;
}
