package com.blibli.future.phase2.command.admin.training.impl;

import com.blibli.future.phase2.command.admin.training.GetAllTrainingCommand;
import com.blibli.future.phase2.entity.Training;
import com.blibli.future.phase2.model.response.admin.training.GetAllTrainingResponse;
import com.blibli.future.phase2.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class GetAllTrainingCommandImpl implements GetAllTrainingCommand {
    @Autowired
    private TrainingRepository trainingRepository;

    @Override
    public Mono<GetAllTrainingResponse> execute(String request) {
        return Mono.fromCallable(() -> getAllTrainings(request))
                .map(trainings -> setDateTraining(trainings))
                .map(trainings -> createResponse(trainings));
    }

    public Set<Training> getAllTrainings(String request){
        return new HashSet<>(Collections.unmodifiableList(
            trainingRepository.findAllByBatchIdOrderByStageAsc(request).collectList().block()
        ));
    }

    public GetAllTrainingResponse createResponse(Set<Training> trainings){
        return GetAllTrainingResponse.builder()
                .trainingList(trainings)
                .build();
    }

    public Set<Training> setDateTraining(Set<Training> trainings){
        trainings.forEach(training -> {
            Instant dateInstant = LocalDate.parse(training.getDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                    .atStartOfDay(ZoneId.systemDefault()).toInstant();
            LocalDateTime date = LocalDateTime.ofInstant(dateInstant, ZoneId.systemDefault());
            training.setDate(date.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy")));
        });

        return trainings;
    }
}
