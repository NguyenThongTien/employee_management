package com.example.employee_management.service;

import com.example.employee_management.dto.response.EmployeeResponse;
import com.example.employee_management.dto.resquest.AssignManageRequest;
import com.example.employee_management.entity.*;
import com.example.employee_management.enums.Roles;
import com.example.employee_management.exceptions.AppException;
import com.example.employee_management.exceptions.ErrorCode;
import com.example.employee_management.mapper.EmployeeMapper;
import com.example.employee_management.repository.AccountRepository;
import com.example.employee_management.repository.EmployeeRepository;
import com.example.employee_management.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssignManageService {
    EmployeeRepository employeeRepository;
    AccountRepository accountRepository;
    RoleRepository roleRepository;
    DepartmentService departmentService;
    PositionService positionService;
    EmployeeMapper employeeMapper;

    public EmployeeResponse assignManage(AssignManageRequest assignManageRequest){
        Department department = departmentService.getDepartment(assignManageRequest.getDepartmentID());
        Position position = positionService.getPosition(assignManageRequest.getPositionID());
        Account account = accountRepository.findById(assignManageRequest.getIdEmployee()).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        Role role = roleRepository.findByNameRole(Roles.MANAGER) .orElseThrow();

        Employee employee = employeeRepository
                .findByAccount(account)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        employee.setDepartment(department);
        employee.setPosition(position);
        employee.setRole(role);

        employeeRepository.save(employee);
        return employeeMapper.toResponse(employee);
    }
}
