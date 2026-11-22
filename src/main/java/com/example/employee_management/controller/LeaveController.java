package com.example.employee_management.controller;

import com.example.employee_management.dto.response.CreateEmployeeLeaveResponse;
import com.example.employee_management.dto.response.LeaveResponse;
import com.example.employee_management.dto.resquest.*;
import com.example.employee_management.entity.Leave;
import com.example.employee_management.exceptions.SuccessCode;
import com.example.employee_management.helper.DataResponse;
import com.example.employee_management.service.LeaveService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leave")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LeaveController {
    LeaveService leaveService;

    @PostMapping("/createLeave")
    DataResponse<CreateEmployeeLeaveResponse> createLeave(CreateEmployeeLeaveRequest createEmployeeLeaveRequest){
        var data = leaveService.createLeave(createEmployeeLeaveRequest);
        return new DataResponse<>(SuccessCode.Success.getCode(), "success", SuccessCode.Success.getMessage(), data);
    }

    @GetMapping("/my-leaves/{employeeId}")
    DataResponse<List<LeaveResponse>> getLeaves(@PathVariable Long employeeId){
        var data = leaveService.getMyLeaves(employeeId);
        return new DataResponse<>(SuccessCode.Success.getCode(), "success", SuccessCode.Success.getMessage(), data);
    }

    @GetMapping("/getLeavesByDepartment/{departmentId}")
    DataResponse<List<LeaveResponse>> getLeavesByDepartment(@PathVariable Long departmentId){
        var data = leaveService.getLeavesByDepartment(departmentId);
        return new DataResponse<>(SuccessCode.Success.getCode(), "success", SuccessCode.Success.getMessage(), data);
    }

    @GetMapping("/getDetailLeaveEmployee")
    DataResponse<LeaveResponse> getDetailLeave(GetDetailLeaveEmployee getDetailLeaveEmployee){
        var data = leaveService.getMyLeaveDetail(getDetailLeaveEmployee);
        return new DataResponse<>(SuccessCode.Success.getCode(), "success", SuccessCode.Success.getMessage(), data);
    }

    @PutMapping("/rejectLeave")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    DataResponse<LeaveResponse> rejectLeave(RejectLeaveRequest cancelLeaveRequest){
        var data = leaveService.rejectLeave(cancelLeaveRequest);
        return new DataResponse<>(SuccessCode.Success.getCode(), "success", SuccessCode.Success.getMessage(), data);
    }

    @PutMapping("/cancelLeave/{leaveId}")
    DataResponse<LeaveResponse> cancelLeave(@PathVariable Long leaveId){
        var data = leaveService.cancelLeave(leaveId);
        return new DataResponse<>(SuccessCode.Success.getCode(), "success", SuccessCode.Success.getMessage(), data);
    }

    @PutMapping("/approveLeave")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    DataResponse<LeaveResponse> approveLeave(ApproveLeaveRequest approveLeaveRequest){
        var data = leaveService.approveLeave(approveLeaveRequest);
        return new DataResponse<>(SuccessCode.Success.getCode(), "success", SuccessCode.Success.getMessage(), data);
    }
}
