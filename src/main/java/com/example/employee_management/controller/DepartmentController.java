package com.example.employee_management.controller;

import com.example.employee_management.dto.response.CreateDepartmentResponse;
import com.example.employee_management.dto.response.DepartmentResponse;
import com.example.employee_management.dto.response.UpdateDepartmentResponse;
import com.example.employee_management.dto.resquest.CreateDepartmentRequest;
import com.example.employee_management.dto.resquest.UpdateDepartmentRequest;
import com.example.employee_management.entity.Department;
import com.example.employee_management.exceptions.SuccessCode;
import com.example.employee_management.helper.DataResponse;
import com.example.employee_management.service.DepartmentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentController {
    DepartmentService departmentService;

    @GetMapping("/getDepartmentActive")
    DataResponse<List<DepartmentResponse>> getDepartmentsActive(){
        var data =  departmentService.getDepartmentsActive();
        return new DataResponse<>(SuccessCode.Success.getCode(), "success", SuccessCode.Success.getMessage(), data);
    }

    @GetMapping("/getDepartmentInActive")
    DataResponse<List<DepartmentResponse>> getDepartmentsInActive(){
        var data =  departmentService.getDepartmentsInActive();
        return new DataResponse<>(SuccessCode.Success.getCode(), "success", SuccessCode.Success.getMessage(), data);
    }

    @GetMapping("/getDepartment/{IdDepartment}")
    DataResponse<Department> getDepartment(@PathVariable Long IdDepartment){
        var data = departmentService.getDepartment(IdDepartment);
        return new DataResponse<>(SuccessCode.Success.getCode(), "success", SuccessCode.Success.getMessage(), data);
    }

    @PostMapping("/createDepartment")
    @PreAuthorize("hasRole('ADMIN')")
    DataResponse<CreateDepartmentResponse> createDepartment(CreateDepartmentRequest createDepartmentRequest){
        var data = departmentService.createDepartment(createDepartmentRequest);
        return new DataResponse<>(SuccessCode.Success.getCode(), "success", SuccessCode.Success.getMessage(), data);
    }

    @PutMapping("/updateDepartment")
    @PreAuthorize("hasRole('ADMIN')")
    DataResponse<UpdateDepartmentResponse> updateDepartment(UpdateDepartmentRequest updateDepartmentRequest){
        var data = departmentService.updateDepartment(updateDepartmentRequest);
        return new DataResponse<>(SuccessCode.Success.getCode(), "success", SuccessCode.Success.getMessage(), data);
    }

    @PutMapping("/InActiveDepartment/{IdDepartment}")
    @PreAuthorize("hasRole('ADMIN')")
    DataResponse<String> inActiveDepartment(@PathVariable Long IdDepartment){
        String data = departmentService.deleteDepartment(IdDepartment);
        return new DataResponse<>(SuccessCode.Success.getCode(), "success", SuccessCode.Success.getMessage(), data);
    }
}
