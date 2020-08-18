package com.blibli.future.phase2.command.admin.training.impl;

import com.blibli.future.phase2.command.admin.training.CreateTrainingCommand;
import com.blibli.future.phase2.entity.Training;
import com.blibli.future.phase2.entity.TrainingAttendance;
import com.blibli.future.phase2.entity.User;
import com.blibli.future.phase2.model.command.admin.training.CreateTrainingRequest;
import com.blibli.future.phase2.model.response.admin.training.CreateTrainingResponse;
import com.blibli.future.phase2.repository.TrainingAttendanceRepository;
import com.blibli.future.phase2.repository.TrainingRepository;
import com.blibli.future.phase2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@Service
public class CreateTrainingCommandImpl implements CreateTrainingCommand {
    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrainingAttendanceRepository trainingAttendanceRepository;

    @Override
    public Mono<CreateTrainingResponse> execute(CreateTrainingRequest request) {
        return Mono.from(checkIfTrainingIsExist(request))
                .flatMap(result -> (result) ? Mono.just(null) : Mono.just(createTraining(request)))
                .flatMap(training -> (training == null) ? Mono.just(null) : trainingRepository.save(training))
                .flatMap(training -> Mono.just(inputAttendance(training.getBatchId())))
                .thenReturn(createResponse(HttpStatus.ACCEPTED, "Training has been created"))
                .onErrorReturn(createResponse(HttpStatus.BAD_REQUEST, "Training cannot be created"));
    }

    private Training createTraining(CreateTrainingRequest request){
        return Training.builder()
                .trainingId(UUID.randomUUID().toString())
                .batchId(request.getBatchId())
                .date(request.getDate())
                .startedAt(request.getTimeStart())
                .endedAt(request.getTimeFinish())
                .location(request.getLocation())
                .stage(Integer.parseInt(request.getTraining()))
                .trainerId(request.getTrainerId())
                .trainerName(request.getTrainerName())
                .build();
    }

    private CreateTrainingResponse createResponse(HttpStatus status, String message){
        return CreateTrainingResponse.builder()
                .status(status)
                .message(message)
                .build();
    }

    public Mono<Boolean> checkIfTrainingIsExist(CreateTrainingRequest request){
        return Mono.from(trainingRepository.findByBatchIdAndStage(request.getBatchId(), Integer.parseInt(request.getTraining()))
                .switchIfEmpty(Mono.just(Training.builder().build()))
                .map(user -> user.getTrainingId() != null));
    }

    private Boolean inputAttendance(String batchId){
        userRepository.findAllByBatch(batchId)
                .map(user -> createAttendance(user))
                .buffer()
                .flatMap(attendances -> trainingAttendanceRepository.saveAll(attendances))
                .then()
                .subscribe();
        return Boolean.TRUE;
    }

    private TrainingAttendance createAttendance(User user){
        return TrainingAttendance.builder()
                .id(UUID.randomUUID().toString())
                .employeeId(user.getUserId())
                .batchId(user.getBatch())
                .stage(user.getStage())
                .division(user.getDivision())
                .username(user.getUsername())
                .status(Boolean.FALSE)
                .build();
    }

    private User getTrainerFromId(String trainerId){
        return userRepository.findById(trainerId).block();
    }
}
