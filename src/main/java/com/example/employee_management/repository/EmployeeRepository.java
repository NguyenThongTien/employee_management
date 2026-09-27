package com.example.employee_management.repository;

import com.example.employee_management.entity.Account;
import com.example.employee_management.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByAccount(Account account);
    // Lấy ra danh sách các cái user chưa được sắp và phòng và vị trí nào
    List<Employee> findByDepartmentIsNullAndPositionIsNull();
}
