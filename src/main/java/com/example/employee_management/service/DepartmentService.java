package com.example.employee_management.service;

import com.example.employee_management.dto.response.CreateDepartmentResponse;
import com.example.employee_management.dto.response.DepartmentResponse;
import com.example.employee_management.dto.response.UpdateDepartmentResponse;
import com.example.employee_management.dto.resquest.CreateDepartmentRequest;
import com.example.employee_management.dto.resquest.UpdateDepartmentRequest;
import com.example.employee_management.entity.Department;
import com.example.employee_management.entity.Employee;
import com.example.employee_management.enums.AccountStatus;
import com.example.employee_management.enums.DepartmentStatus;
import com.example.employee_management.exceptions.AppException;
import com.example.employee_management.exceptions.ErrorCode;
import com.example.employee_management.exceptions.SuccessCode;
import com.example.employee_management.mapper.DepartmentMapper;
import com.example.employee_management.repository.DepartmentRepository;
import com.example.employee_management.repository.EmployeeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentService {
    DepartmentRepository departmentRepository;
    EmployeeRepository employeeRepository;
    DepartmentMapper departmentMapper;

    public List<DepartmentResponse> getDepartmentsActive() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentResponse> departmentActive = new ArrayList<>();
        for (Department department : departments) {
            if (department.getStatus() == DepartmentStatus.ACTIVE) {
                DepartmentResponse response =
                        departmentMapper.toResponseDepartmentResponse(department);
                departmentActive.add(response);
            }
        }
        return departmentActive;
    }

    public List<DepartmentResponse> getDepartmentsInActive() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentResponse> departmentInActive = new ArrayList<>();
        for (Department department : departments) {
            if (department.getStatus() == DepartmentStatus.INACTIVE) {
                DepartmentResponse response =
                        departmentMapper.toResponseDepartmentResponse(department);
                departmentInActive.add(response);
            }
        }
        return departmentInActive;
    }

    public Department getDepartment(Long departmentID){
        return departmentRepository.findById(departmentID)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND) );
    }

    public CreateDepartmentResponse createDepartment(CreateDepartmentRequest createDepartmentRequest){
        if(departmentRepository.existsByDepartmentName(createDepartmentRequest.getDepartmentName())){
            throw new AppException(ErrorCode.DEPARTMENT_ALREADY_EXISTS);
        }
        Department department = departmentMapper.toEntity(createDepartmentRequest);
        department.setStatus(DepartmentStatus.ACTIVE);
        departmentRepository.save(department);

        CreateDepartmentResponse createDepartment = departmentMapper.toResponse(department);
        createDepartment.setStatus(DepartmentStatus.ACTIVE);
        return createDepartment;
    }

    public UpdateDepartmentResponse updateDepartment(UpdateDepartmentRequest updateDepartmentRequest){
        Department department = getDepartment(updateDepartmentRequest.getId());
        department.setDepartmentName(updateDepartmentRequest.getDepartmentName());
        department.setBuilding(updateDepartmentRequest.getBuilding());
        department.setFloor(updateDepartmentRequest.getFloor());
        department.setStatus(DepartmentStatus.ACTIVE);
        departmentRepository.save(department);
        UpdateDepartmentResponse updateDepartment = departmentMapper.toUpdateResponse(department);
        updateDepartment.setStatus(DepartmentStatus.ACTIVE);
        return updateDepartment;
    }

    public String deleteDepartment(Long departmentID) {
        Department department = departmentRepository
                .findById(departmentID)
                .orElseThrow(() ->
                        new AppException(ErrorCode.DEPARTMENT_NOT_EXISTS)
                );
        List<Employee> employees =
                employeeRepository.findAll();
        for (Employee employee : employees) {
            if (employee.getDepartment() != null
                    && employee.getDepartment().getId().equals(departmentID)
                    && employee.getAccount().getStatus() == AccountStatus.ACTIVE) {
                throw new AppException(
                        ErrorCode.DEPARTMENT_NOT_DELETE
                );
            }
        }
        department.setStatus(DepartmentStatus.INACTIVE);
        departmentRepository.save(department);
        return SuccessCode.Success.getMessage();
    }
}
