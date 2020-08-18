package com.blibli.future.phase2.command.employee.training.impl;

import com.blibli.future.phase2.command.employee.training.CheckAttendanceEmplCommand;
import com.blibli.future.phase2.component.AuthenticatedUserProvider;
import com.blibli.future.phase2.model.command.user.training.CheckAttendanceEmplRequest;
import com.blibli.future.phase2.model.response.user.training.CheckAttendanceEmplResponse;
import com.blibli.future.phase2.repository.TrainingAttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CheckAttendanceEmplCommandImpl implements CheckAttendanceEmplCommand {
    @Autowired
    private TrainingAttendanceRepository trainingAttendanceRepository;

    @Autowired
    private AuthenticatedUserProvider authenticatedUserProvider;

    @Override
    public Mono<CheckAttendanceEmplResponse> execute(CheckAttendanceEmplRequest request) {
        return Mono.from(checkAttendance(request))
                .map(result -> createResponse(result));
    }

    private Mono<Boolean> checkAttendance(CheckAttendanceEmplRequest request){
        return trainingAttendanceRepository.existsByEmployeeIdAndBatchIdAndStageAndStatus(
                (String) authenticatedUserProvider.getValueFromUserData(request.getToken(), "userId"),
                (String) authenticatedUserProvider.getValueFromUserData(request.getToken(), "batch"),
                Integer.parseInt(request.getTraining()),
                Boolean.TRUE
        );
    }

    private CheckAttendanceEmplResponse createResponse(Boolean result){
        return CheckAttendanceEmplResponse.builder()
                .hasAttend(result)
                .build();
    }
}
