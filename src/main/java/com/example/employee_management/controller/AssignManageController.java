package com.example.employee_management.controller;

import com.example.employee_management.dto.response.EmployeeResponse;
import com.example.employee_management.dto.resquest.AssignManageRequest;
import com.example.employee_management.dto.resquest.AssignmentEmployeeRequest;
import com.example.employee_management.entity.Employee;
import com.example.employee_management.exceptions.SuccessCode;
import com.example.employee_management.helper.DataResponse;
import com.example.employee_management.service.AssignManageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manage")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssignManageController {
    AssignManageService assignManageService;

    @PutMapping("/assignManage")
    @PreAuthorize("hasAnyRole('ADMIN')")
    DataResponse<EmployeeResponse> assignmentEmployee(AssignManageRequest assignManageRequest){
        var data = assignManageService.assignManage(assignManageRequest);
        return new DataResponse<>(SuccessCode.Success.getCode(), "success", SuccessCode.Success.getMessage(), data);
    }
}
