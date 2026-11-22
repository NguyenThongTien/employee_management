package com.example.employee_management.service;

import com.example.employee_management.dto.response.CreatePositionResponse;
import com.example.employee_management.dto.response.UpdatePositionResponse;
import com.example.employee_management.dto.resquest.CreatePositionRequest;
import com.example.employee_management.dto.resquest.UpdatePositionRequest;
import com.example.employee_management.entity.Position;
import com.example.employee_management.exceptions.AppException;
import com.example.employee_management.repository.PositionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import static com.example.employee_management.exceptions.ErrorCode.POSITION_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PositionService {
    PositionRepository positionRepository;

    public List<Position> getPositions(){
        return positionRepository.findAll();
    }

    public Position getPosition(Long positionId){
        return positionRepository.findById(positionId).orElseThrow(() -> new AppException(POSITION_NOT_FOUND));
    }

    public CreatePositionResponse createPosition(CreatePositionRequest createPositionRequest){
        Position position = new Position();
        position.setNamePosition(createPositionRequest.getPositionName());
        positionRepository.save(position);
        CreatePositionResponse createPositionResponse = new CreatePositionResponse();
        createPositionResponse.setNamePosition(createPositionRequest.getPositionName());
        return createPositionResponse;
    }

    public UpdatePositionResponse updatePosition(UpdatePositionRequest updatePositionRequest){
        Position position = positionRepository.findById(updatePositionRequest.getId()).orElseThrow(() -> new AppException(POSITION_NOT_FOUND));
        position.setNamePosition(updatePositionRequest.getPositionName());
        positionRepository.save(position);
        UpdatePositionResponse updatePositionResponse = new UpdatePositionResponse();
        updatePositionResponse.setNamePosition(updatePositionRequest.getPositionName());
        return updatePositionResponse;
    }
}
