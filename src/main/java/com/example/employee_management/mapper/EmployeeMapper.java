package com.example.employee_management.mapper;

import com.example.employee_management.dto.response.EmployeeResponse;
import com.example.employee_management.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.departmentName", target = "departmentName")
    @Mapping(source = "position.id", target = "positionId")
    @Mapping(source = "position.namePosition", target = "positionName")

    EmployeeResponse toResponse(Employee employee);

    List<EmployeeResponse> toResponseList(List<Employee> employees);
}
