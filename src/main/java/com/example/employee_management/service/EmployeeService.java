package com.example.employee_management.service;

import com.example.employee_management.dto.resquest.AssignmentEmployeeRequest;
import com.example.employee_management.entity.Account;
import com.example.employee_management.entity.Employee;
import com.example.employee_management.exceptions.AppException;
import com.example.employee_management.exceptions.ErrorCode;
import com.example.employee_management.repository.AccountRepository;
import com.example.employee_management.repository.EmployeeRepository;
import com.example.employee_management.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.employee_management.exceptions.ErrorCode.ACCOUNT_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeService {
    EmployeeRepository employeeRepository;
    AccountRepository accountRepository;

    public List<Employee> getEmployees(){
        return employeeRepository.findAll();
    }

    public Employee getEmployee(Long IdEmployee){
        return employeeRepository.findById(IdEmployee).orElseThrow(() -> new AppException(ACCOUNT_NOT_FOUND));
    }

    // Lấy ra danh sách các cái user chưa được sắp và phòng và vị trí nào
    public List<Employee> getUnassignedEmployees() {
        return employeeRepository.findByDepartmentIsNullAndPositionIsNull();
    }


    public Employee updateAssignmentEmployee(AssignmentEmployeeRequest assignmentEmployeeRequest){
        Account account = accountRepository.findById(assignmentEmployeeRequest.getIdEmployee()).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        Employee employee = employeeRepository
                .findByAccount(account)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
//        employee.setDepartment();

        return employee;
    }

}
