package com.example.employee_management.mapper;

import com.example.employee_management.dto.response.AccountDTO;
import com.example.employee_management.entity.Account;
import com.example.employee_management.entity.Employee;
import org.mapstruct.Mapper;

//@Mapper = MapStruct tạo code mapping.
//componentModel = "spring" = để Spring quản lý Mapper đó và inject được vào Service.
@Mapper(componentModel = "spring")
public interface AccountMapper {
    public static AccountDTO toDTO(Account account, Employee employee){
        AccountDTO dto = new AccountDTO();
        dto.setId(account.getId());
        dto.setEmail(account.getEmail());
        dto.setUsername(account.getUsername());
        dto.setPhoneNumber(account.getPhoneNumber());
        dto.setBirthDay(account.getBirthDay());
        dto.setGender(account.getGender());
        dto.setAvatar(account.getAvatar());
        dto.setFullName(account.getFullName());
        dto.setStatus(account.getStatus());
        if (employee != null && employee.getRole() != null) {
            dto.setNameRole(employee.getRole().getNameRole());
        }

        return dto;
    }
}
