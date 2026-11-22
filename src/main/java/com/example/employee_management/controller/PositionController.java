package com.example.employee_management.controller;

import com.example.employee_management.dto.response.CreatePositionResponse;
import com.example.employee_management.dto.response.UpdatePositionResponse;
import com.example.employee_management.dto.resquest.CreatePositionRequest;
import com.example.employee_management.dto.resquest.UpdatePositionRequest;
import com.example.employee_management.entity.Position;
import com.example.employee_management.exceptions.SuccessCode;
import com.example.employee_management.helper.DataResponse;
import com.example.employee_management.service.PositionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/position")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PositionController {
    PositionService positionService;

    @GetMapping("/getPositions")
    DataResponse<List<Position>> getPositions(){
        var data =  positionService.getPositions();
        return new DataResponse<>(SuccessCode.Success.getCode(), "success", SuccessCode.Success.getMessage(), data);
    }

    @GetMapping("/getPosition/{IdPosition}")
    DataResponse<Position> getPosition(@PathVariable Long IdPosition){
        var data =  positionService.getPosition(IdPosition);
        return new DataResponse<>(SuccessCode.Success.getCode(), "success", SuccessCode.Success.getMessage(), data);
    }

    @PostMapping("/createPosition")
    @PreAuthorize("hasRole('ADMIN')")
    DataResponse<CreatePositionResponse> createPosition(CreatePositionRequest createPositionRequest){
        var data = positionService.createPosition(createPositionRequest);
        return new DataResponse<>(SuccessCode.Success.getCode(), "success", SuccessCode.Success.getMessage(), data);
    }

    @PutMapping("/updatePosition")
    @PreAuthorize("hasRole('ADMIN')")
    DataResponse<UpdatePositionResponse> updatePosition(UpdatePositionRequest updatePositionRequest){
        var data = positionService.updatePosition(updatePositionRequest);
        return new DataResponse<>(SuccessCode.Success.getCode(), "success", SuccessCode.Success.getMessage(), data);
    }


}
