package com.example.employee_management.controller;

import com.example.employee_management.dto.response.AccountDTO;
import com.example.employee_management.dto.response.LoginResponse;
import com.example.employee_management.dto.resquest.LoginRequest;
import com.example.employee_management.dto.resquest.RegisterRequest;
import com.example.employee_management.exceptions.SuccessCode;
import com.example.employee_management.helper.DataResponse;
import com.example.employee_management.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthService authService;

    @PostMapping("/register")
    DataResponse<AccountDTO> register(@RequestBody RegisterRequest request){
        var data =  authService.register(request);
        return new DataResponse<>(SuccessCode.Success.getCode(), "success", SuccessCode.Success.getMessage(), data);
    }

    @PostMapping("/login")
    DataResponse<LoginResponse> register(@RequestBody LoginRequest request){
        var data =  authService.login(request);
        return new DataResponse<>(SuccessCode.Success.getCode(), "success", SuccessCode.Success.getMessage(), data);
    }
}
