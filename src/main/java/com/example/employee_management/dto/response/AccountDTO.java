package com.example.employee_management.dto.response;

import com.example.employee_management.enums.AccountStatus;
import com.example.employee_management.enums.Roles;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDTO {
    Long id;
    String username;
    String avatar;
    String gender;
    String fullName;
    @Enumerated(EnumType.STRING) // Nó bảo JPA lưu enum dưới dạng String trong database
    AccountStatus status;
    String phoneNumber;
    String email;
    String birthDay;
    @Enumerated(EnumType.STRING)
    Roles nameRole;
}
