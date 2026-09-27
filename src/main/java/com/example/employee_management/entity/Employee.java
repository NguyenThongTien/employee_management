package com.example.employee_management.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String employeeCode;
    String hireDate;
    @OneToOne
    Account account;

    @ManyToOne
    @JoinColumn(name = "department_id") // cái này là tên cột trong DB nên có thể đặt tên thoải mái
    private Department department;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    @ManyToOne
    @JoinColumn(name = "leave_id")
    private Leave leave;

    @ManyToOne
    @JoinColumn(name = "attendance_id")
    private Attendance attendance;
}
