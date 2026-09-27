package com.example.employee_management.service;

import com.example.employee_management.dto.response.AccountDTO;
import com.example.employee_management.dto.response.LoginResponse;
import com.example.employee_management.dto.resquest.LoginRequest;
import com.example.employee_management.dto.resquest.RegisterRequest;
import com.example.employee_management.entity.Account;
import com.example.employee_management.entity.Employee;
import com.example.employee_management.entity.Role;
import com.example.employee_management.enums.AccountStatus;
import com.example.employee_management.enums.Roles;
import com.example.employee_management.exceptions.AppException;
import com.example.employee_management.exceptions.ErrorCode;
import com.example.employee_management.mapper.AccountMapper;
import com.example.employee_management.repository.AccountRepository;
import com.example.employee_management.repository.EmployeeRepository;
import com.example.employee_management.repository.RoleRepository;
import com.example.employee_management.security.jwt.JwtUtil;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {
    AccountRepository accountRepository;
    RoleRepository roleRepository;
    EmployeeRepository employeeRepository;
    JwtUtil jwtUtil;


    public AccountDTO register(RegisterRequest request) {
        if (accountRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        if (accountRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
            throw new AppException(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS);
        }
        // Lấy role mặc định là Employee
        Role role = roleRepository.findByNameRole(Roles.EMPLOYEE) .orElseThrow();
        // Nếu là số điện thoại đặc biệt thì gán quyền ADMIN
//        if ("0985006580".equalsIgnoreCase(request.getPhoneNumber())) {
//            role = rolesRepository.findByName("ADMIN");
//        }

        Account account = new Account();
        account.setEmail(request.getEmail());
        account.setUsername(request.getUsername());
//        account.setAvatar(request.getAvatar());
        account.setGender(request.getGender());
        account.setFullName(request.getFullName());
        account.setPhoneNumber(request.getPhoneNumber());
        account.setBirthDay(request.getBirthday());
//        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setStatus(AccountStatus.ACTIVE);

        Account savedAccount = accountRepository.save(account);

        Employee employee = new Employee();

//        employee.setEmployeeCode(request.getEmployeeCode());
//        employee.setHireDate(request.getHireDate());
        employee.setAccount(savedAccount);
        employee.setRole(role);
        employee.setDepartment(null);
        employee.setPosition(null);
        employee.setLeave(null);
        employee.setAttendance(null);

        Employee savedEmployee = employeeRepository.save(employee);

        Account saved = accountRepository.save(account);
        return AccountMapper.toDTO(saved, savedEmployee);
    }

    public LoginResponse login(LoginRequest request){
        Account account = accountRepository.findByEmailOrPhoneNumber(request.getPhoneOrEmail(), request.getPhoneOrEmail())
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        Employee employee = employeeRepository
                .findByAccount(account)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        String token = jwtUtil.generateToken(account, employee.getRole().getNameRole().name());
        AccountDTO accountDTO = AccountMapper.toDTO(account, employee);
        return new LoginResponse(token, accountDTO);
    }
}
