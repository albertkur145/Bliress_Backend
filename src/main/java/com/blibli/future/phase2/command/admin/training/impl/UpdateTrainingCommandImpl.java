package com.blibli.future.phase2.command.admin.training.impl;

import com.blibli.future.phase2.command.admin.training.UpdateTrainingCommand;
import com.blibli.future.phase2.entity.Training;
import com.blibli.future.phase2.entity.User;
import com.blibli.future.phase2.model.command.admin.training.UpdateTrainingRequest;
import com.blibli.future.phase2.model.response.admin.training.UpdateTrainingResponse;
import com.blibli.future.phase2.repository.TrainingRepository;
import com.blibli.future.phase2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class UpdateTrainingCommandImpl implements UpdateTrainingCommand {
    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<UpdateTrainingResponse> execute(UpdateTrainingRequest request) {
        return Mono.from(getTraining(request.getBatchId(), Integer.parseInt(request.getTraining())))
                .map(training -> updateTraining(training, request))
                .flatMap(training -> trainingRepository.save(training))
                .map(training -> createResponse(HttpStatus.ACCEPTED, "Training has been updated"))
                .onErrorReturn(createResponse(HttpStatus.BAD_REQUEST, "Training cannot be updated"));
    }

    private Mono<Training> getTraining(String batchId, Integer stage){
        return trainingRepository.findByBatchIdAndStage(batchId, stage);
    }

    private Training updateTraining(Training training, UpdateTrainingRequest request){
        training.setDate(request.getDate());
        training.setStartedAt(request.getTimeStart());
        training.setEndedAt(request.getTimeFinish());
        training.setLocation(request.getLocation());
        training.setTrainerId(request.getTrainerId());
        training.setTrainerName(request.getTrainerName());

        return training;
    }

    private UpdateTrainingResponse createResponse(HttpStatus status, String message){
        return UpdateTrainingResponse.builder()
                .status(status)
                .message(message)
                .build();
    }

    private User getTrainerFromId(String trainerId){
        return userRepository.findById(trainerId).block();
    }
}
