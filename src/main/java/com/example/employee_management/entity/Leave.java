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

    @OneToMany(mappedBy = "leave")
    private List<Employee> employees;
}
