package com.example.employee_management.service;

import com.example.employee_management.dto.response.CreateEmployeeLeaveResponse;
import com.example.employee_management.dto.response.LeaveResponse;
import com.example.employee_management.dto.resquest.ApproveLeaveRequest;
import com.example.employee_management.dto.resquest.RejectLeaveRequest;
import com.example.employee_management.dto.resquest.CreateEmployeeLeaveRequest;
import com.example.employee_management.dto.resquest.GetDetailLeaveEmployee;
import com.example.employee_management.entity.Employee;
import com.example.employee_management.entity.Leave;
import com.example.employee_management.enums.LeaveStatus;
import com.example.employee_management.enums.Roles;
import com.example.employee_management.exceptions.AppException;
import com.example.employee_management.exceptions.ErrorCode;
import com.example.employee_management.mapper.LeaveMapper;
import com.example.employee_management.repository.EmployeeRepository;
import com.example.employee_management.repository.LeaveRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.employee_management.exceptions.ErrorCode.ACCOUNT_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LeaveService {
    LeaveRepository leaveRepository;
    EmployeeRepository employeeRepository;
    LeaveMapper leaveMapper;

    public CreateEmployeeLeaveResponse createLeave(CreateEmployeeLeaveRequest createEmployeeLeaveRequest){
        Employee employee = employeeRepository.findById(createEmployeeLeaveRequest.getEmployeeId()).orElseThrow(() -> new AppException(ACCOUNT_NOT_FOUND));;
        Leave leave = new Leave();
        LocalDateTime now = LocalDateTime.now();
        leave.setEndTime(createEmployeeLeaveRequest.getEndTime());
        leave.setStartTime(createEmployeeLeaveRequest.getStartTime());
        leave.setCreatedAt(now.toString());
        leave.setReason(createEmployeeLeaveRequest.getReason());
        leave.setStatus(LeaveStatus.PENDING);
        leave.setEmployee(employee);
        leaveRepository.save(leave);
        employeeRepository.save(employee);
        return leaveMapper.toResponse(leave);
    }

    public List<LeaveResponse> getMyLeaves(Long employeeId) {
        return leaveMapper.toLeaveResponse(leaveRepository.findByEmployeeId(employeeId));
    }

    public List<LeaveResponse> getLeavesByDepartment(Long departmentId) {
        return leaveMapper.toLeaveResponse(leaveRepository.findByStatusAndEmployeeDepartmentId(LeaveStatus.PENDING, departmentId));
    }

    public LeaveResponse getMyLeaveDetail(GetDetailLeaveEmployee getDetailLeaveEmployee) {
        return leaveMapper.toLeaveResponse(leaveRepository
                .findByIdAndEmployeeId(getDetailLeaveEmployee.getLeaveId(), getDetailLeaveEmployee.getEmployeeId())
                .orElseThrow(() -> new AppException(ErrorCode.LEAVE_NOT_FOUND)));
    }

    public LeaveResponse rejectLeave(RejectLeaveRequest rejectLeaveRequest) {
        Employee employee = employeeRepository.findById(rejectLeaveRequest.getEmployeeId()).orElseThrow(() -> new AppException(ACCOUNT_NOT_FOUND));
        Employee manage = employeeRepository.findById(rejectLeaveRequest.getManageIdOrAdmin()).orElseThrow(() -> new AppException(ACCOUNT_NOT_FOUND));
        Leave leave = leaveRepository.findById(rejectLeaveRequest.getLeaveId()).orElseThrow(() -> new AppException(ErrorCode.LEAVE_NOT_FOUND));
        if (leave.getStatus() != LeaveStatus.PENDING) {
            throw new AppException(ErrorCode.LEAVE_NOT_CANCEL);
        }

        if(manage.getRole().getNameRole().equals(Roles.ADMIN) || (manage.getRole().getNameRole().equals(Roles.MANAGER)
                && manage.getDepartment().getId()
                        .equals(employee.getDepartment().getId())
        )){
            leave.setStatus(LeaveStatus.REJECTED);
            leave.setReason(rejectLeaveRequest.getReasonLeave());
        }else{
            throw new AppException(ErrorCode.LEAVE_NOT_CANCEL);
        }
        leaveRepository.save(leave);

        return leaveMapper.toLeaveResponse(leave);
    }

    public LeaveResponse cancelLeave(Long leaveId){
        Leave leave = leaveRepository.findById(leaveId).orElseThrow(() -> new AppException(ErrorCode.LEAVE_NOT_FOUND));
        leave.setStatus(LeaveStatus.CANCELLED);
        leaveRepository.save(leave);
        return leaveMapper.toLeaveResponse(leave);
    }


    public LeaveResponse approveLeave(ApproveLeaveRequest approveLeaveRequest) {
        Employee employee = employeeRepository.findById(approveLeaveRequest.getEmployeeId()).orElseThrow(() -> new AppException(ACCOUNT_NOT_FOUND));
        Employee manage = employeeRepository.findById(approveLeaveRequest.getManageIdOrAdmin()).orElseThrow(() -> new AppException(ACCOUNT_NOT_FOUND));
        Leave leave = leaveRepository.findById(approveLeaveRequest.getLeaveId()).orElseThrow(() -> new AppException(ErrorCode.LEAVE_NOT_FOUND));

        if(manage.getRole().getNameRole().equals(Roles.ADMIN) || (manage.getRole().getNameRole().equals(Roles.MANAGER)
                && manage.getDepartment().getId()
                .equals(employee.getDepartment().getId())
        )){
            leave.setStatus(LeaveStatus.APPROVED);
        }else{
            throw new AppException(ErrorCode.LEAVE_NOT_APPROVE);
        }
        leaveRepository.save(leave);
        return leaveMapper.toLeaveResponse(leave);
    }

}
