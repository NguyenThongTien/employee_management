package com.example.employee_management.service;

import com.example.employee_management.dto.response.EmployeeResponse;
import com.example.employee_management.dto.resquest.AssignmentEmployeeRequest;
import com.example.employee_management.entity.Account;
import com.example.employee_management.entity.Department;
import com.example.employee_management.entity.Employee;
import com.example.employee_management.entity.Position;
import com.example.employee_management.enums.AccountStatus;
import com.example.employee_management.exceptions.AppException;
import com.example.employee_management.exceptions.ErrorCode;
import com.example.employee_management.mapper.EmployeeMapper;
import com.example.employee_management.repository.AccountRepository;
import com.example.employee_management.repository.EmployeeRepository;
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
    DepartmentService departmentService;
    PositionService positionService;
    EmployeeMapper employeeMapper;

    public List<EmployeeResponse> getEmployees(){
        return employeeMapper.toResponseList(employeeRepository.findAll());
    }

    public EmployeeResponse getEmployee(Long IdEmployee){
        return employeeMapper.toResponse(employeeRepository.findById(IdEmployee).orElseThrow(() -> new AppException(ACCOUNT_NOT_FOUND)));
    }

    // Lấy ra danh sách các cái user chưa được sắp và phòng và vị trí nào
    public List<EmployeeResponse> getUnassignedEmployees() {
        return employeeMapper.toResponseList(employeeRepository.findByDepartmentIsNullAndPositionIsNull());
    }

    // lấy ra danh sách nhân viên trong 1 phòng
    public List<EmployeeResponse> getEmployeesByDepartment(Long departmentId) {
        return employeeMapper.toResponseList(employeeRepository.findByDepartmentId(departmentId));
    }

    public EmployeeResponse updateAssignmentEmployee(AssignmentEmployeeRequest assignmentEmployeeRequest){
        Department department = departmentService.getDepartment(assignmentEmployeeRequest.getDepartmentID());
        Position position = positionService.getPosition(assignmentEmployeeRequest.getPositionID());
        Account account = accountRepository.findById(assignmentEmployeeRequest.getIdEmployee()).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        Employee employee = employeeRepository
                .findByAccount(account)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        employee.setDepartment(department);
        employee.setPosition(position);
        return employeeMapper.toResponse(employee);
    }

    public EmployeeResponse inActiveEmployee(Long employeeId){
        Account account = accountRepository.findById(employeeId).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        account.setStatus(AccountStatus.INACTIVE);
        return employeeMapper.toResponse(employeeRepository
                .findByAccount(account)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND)));
    }

}
