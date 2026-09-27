package com.example.employee_management.entity;
import com.example.employee_management.enums.DepartmentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long Id;
    String departmentName;
    @Enumerated(EnumType.STRING)
    DepartmentStatus status;
    String floor;
    String building;
    // Department
    // chỗ mappedBy phải đặt trùng với tên field ở trong bảng Employee để có thể hiểu là
    // Department.employees đang mapping tới relationship Employee.department
    @OneToMany(mappedBy = "department")
    private List<Employee> employees;
}
