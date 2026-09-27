package com.example.employee_management.controller;

import com.example.employee_management.entity.Employee;
import com.example.employee_management.exceptions.SuccessCode;
import com.example.employee_management.helper.DataResponse;
import com.example.employee_management.service.EmployeeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeController {
    EmployeeService employeeService;

    @GetMapping("/getEmployees")
    DataResponse<List<Employee>> getEmployees(){
        var data =  employeeService.getEmployees();
        return new DataResponse<>(SuccessCode.Success.getCode(), "success", SuccessCode.Success.getMessage(), data);
    }

    @GetMapping("/getEmployee/{IdEmployee}")
    DataResponse<Employee> getEmployee(@PathVariable Long IdEmployee){
        var data =  employeeService.getEmployee(IdEmployee);
        return new DataResponse<>(SuccessCode.Success.getCode(), "success", SuccessCode.Success.getMessage(), data);
    }

    // Lấy ra danh sách các cái user chưa được sắp và phòng và vị trí nào
    @GetMapping("/unassigned")
    DataResponse<List<Employee>> getUnassignedEmployees() {
        var data =  employeeService.getUnassignedEmployees();
        return new DataResponse<>(SuccessCode.Success.getCode(), "success", SuccessCode.Success.getMessage(), data);
    }
    
}
