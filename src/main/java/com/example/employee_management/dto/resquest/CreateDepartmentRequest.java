package com.example.employee_management.dto.resquest;

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
public class CreateDepartmentRequest {
    String departmentName;
    String floor;
    String building;
}
