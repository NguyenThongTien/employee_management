package com.example.employee_management.dto.resquest;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {
    String email;
    String username;
    String phoneNumber;
    String password;
    String fullName;
    String phone;
    String gender;
    String birthday;
}
