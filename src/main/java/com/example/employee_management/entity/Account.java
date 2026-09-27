package com.example.employee_management.entity;

import com.example.employee_management.enums.AccountStatus;
import com.example.employee_management.enums.LeaveStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String username;
    String avatar;
    String gender;
    String password;
    String fullName;
    @Enumerated(EnumType.STRING) // Nó bảo JPA lưu enum dưới dạng String trong database
    AccountStatus status;
    String phoneNumber;
    String email;
    String birthDay;
}
