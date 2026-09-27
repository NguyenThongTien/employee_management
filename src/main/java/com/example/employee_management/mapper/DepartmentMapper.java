package com.example.employee_management.mapper;

import com.example.employee_management.dto.response.CreateDepartmentResponse;
import com.example.employee_management.dto.response.DepartmentResponse;
import com.example.employee_management.dto.response.UpdateDepartmentResponse;
import com.example.employee_management.dto.resquest.CreateDepartmentRequest;
import com.example.employee_management.dto.resquest.UpdateDepartmentRequest;
import com.example.employee_management.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    // Chuyển dữ liệu từ CreateDepartmentRequest → Department
    Department toEntity(CreateDepartmentRequest request);

    // Hãy lấy dữ liệu từ UpdateDepartmentRequest và ghi đè/cập nhật vào chính object Department này.
    // Do đây chỉ là update nên ko tạo mới object
    void updateEntity(
            UpdateDepartmentRequest request,
            @MappingTarget Department department);

    // Chuyển dữ liệu từ Department → CreateDepartmentResponse
    CreateDepartmentResponse toResponse(Department department);

    // Department → UpdateRequest
    UpdateDepartmentResponse toUpdateResponse(Department department);

    // Department → DepartmentResponse
    DepartmentResponse toResponseDepartmentResponse(Department department);
    // DepartmentResponse → Department
    Department toEntityDepartmentResponse(DepartmentResponse response);
}
