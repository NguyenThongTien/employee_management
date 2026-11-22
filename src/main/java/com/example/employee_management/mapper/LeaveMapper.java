package com.example.employee_management.mapper;

import com.example.employee_management.dto.response.CreateEmployeeLeaveResponse;
import com.example.employee_management.dto.response.LeaveResponse;
import com.example.employee_management.dto.resquest.CreateEmployeeLeaveRequest;
import com.example.employee_management.entity.Leave;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LeaveMapper {

    // Request -> Entity
    Leave toEntity(CreateEmployeeLeaveRequest request);

    // Entity -> Response khi tạo leave
    CreateEmployeeLeaveResponse toResponse(Leave leave);

    // Entity -> Response khi GET leave
    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.employeeCode", target = "employeeCode")
    @Mapping(source = "employee.account.fullName", target = "employeeName")
    LeaveResponse toLeaveResponse(Leave leave);

    // List<Entity> -> List<Response>
    List<LeaveResponse> toLeaveResponse(List<Leave> leaves);
}