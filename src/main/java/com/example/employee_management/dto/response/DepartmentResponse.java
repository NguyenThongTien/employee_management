package com.example.employee_management.dto.response;

import com.example.employee_management.enums.DepartmentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentResponse {
    Long Id;
    String departmentName;
    @Enumerated(EnumType.STRING)
    DepartmentStatus status;
    String floor;
    String building;
}
