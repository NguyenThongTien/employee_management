package com.example.employee_management.dto.resquest;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateDepartmentRequest {
    Long id;
    String departmentName;
    String floor;
    String building;
}
