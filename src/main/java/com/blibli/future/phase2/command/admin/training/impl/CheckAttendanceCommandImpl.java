package com.blibli.future.phase2.command.admin.training.impl;

import com.blibli.future.phase2.command.admin.training.CheckAttendanceCommand;
import com.blibli.future.phase2.entity.TrainingAttendance;
import com.blibli.future.phase2.model.command.admin.training.CheckAttendanceRequest;
import com.blibli.future.phase2.model.response.admin.training.CheckAttendanceResponse;
import com.blibli.future.phase2.repository.TrainingAttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

@Service
public class CheckAttendanceCommandImpl implements CheckAttendanceCommand {
    @Autowired
    private TrainingAttendanceRepository trainingAttendanceRepository;

    @Override
    public Mono<CheckAttendanceResponse> execute(CheckAttendanceRequest request) {
        return Mono.fromCallable(() -> getAllAttendance(request))
                .map(attendances -> createResponse(attendances));
    }

    private Set<TrainingAttendance> getAllAttendance(CheckAttendanceRequest request){
        return new HashSet<>(
                trainingAttendanceRepository.findAllByBatchIdAndStage(
                        request.getBatchId(), Integer.parseInt(request.getTraining())
                ).collectList().block()
        );
    }

    private CheckAttendanceResponse createResponse(Set<TrainingAttendance> trainingAttendances){
        return CheckAttendanceResponse.builder()
                .employeeList(trainingAttendances)
                .build();
    }
}
