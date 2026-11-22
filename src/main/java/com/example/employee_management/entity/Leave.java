package com.example.employee_management.entity;

import com.example.employee_management.enums.LeaveStatus;
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
@Table(name = "Leave")
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long Id;
    String startTime;
    String endTime;
    String reason;
    @Enumerated(EnumType.STRING) // Nó bảo JPA lưu enum dưới dạng String trong database
    LeaveStatus status;
    String createdAt;
    String approvedAt;
    String rejectReason;
    // Một Leave thuộc về một Employee
    // Một Employee có thể có nhiều Leave
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
}
