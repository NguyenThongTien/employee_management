package com.example.employee_management.repository;

import com.example.employee_management.entity.Leave;
import com.example.employee_management.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LeaveRepository extends JpaRepository<Leave, Long> {
    List<Leave> findByEmployeeId(Long employeeId);
    Optional<Leave> findByIdAndEmployeeId(Long leaveId, Long employeeId);
    List<Leave> findByStatusAndEmployeeDepartmentId(
            LeaveStatus status,
            Long departmentId
    );
}
