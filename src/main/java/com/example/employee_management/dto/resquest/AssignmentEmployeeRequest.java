package com.example.employee_management.dto.resquest;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssignmentEmployeeRequest {
    Long IdEmployee;
    Long departmentID;
    Long positionID;
}
