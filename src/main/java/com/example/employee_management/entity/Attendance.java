package com.example.employee_management.entity;

import com.example.employee_management.enums.AttendanceStatus;
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
@Table(name = "Attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long Id;
    String date;
    String checkIn;
    String checkout;
    @Enumerated(EnumType.STRING)
    AttendanceStatus status;

    @OneToMany(mappedBy = "attendance")
    private List<Employee> employees;
}
