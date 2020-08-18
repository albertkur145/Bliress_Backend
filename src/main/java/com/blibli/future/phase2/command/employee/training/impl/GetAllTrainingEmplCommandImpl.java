package com.blibli.future.phase2.command.employee.training.impl;

import com.blibli.future.phase2.command.employee.training.GetAllTrainingEmplCommand;
import com.blibli.future.phase2.component.AuthenticatedUserProvider;
import com.blibli.future.phase2.entity.Training;
import com.blibli.future.phase2.model.response.user.training.GetAllTrainingEmplResponse;
import com.blibli.future.phase2.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

@Service
public class GetAllTrainingEmplCommandImpl implements GetAllTrainingEmplCommand {
    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private AuthenticatedUserProvider authenticatedUserProvider;

    @Override
    public Mono<GetAllTrainingEmplResponse> execute(String request) {
        return Mono.fromCallable(() -> getAllTraining(
                (String) authenticatedUserProvider.getValueFromUserData(request, "batch")
        ))
                .map(trainings -> createResponse(trainings));
    }

    private Set<Training> getAllTraining(String batchId){
        return new HashSet<>(
                trainingRepository.findAllByBatchIdOrderByStageAsc(batchId)
                        .collectList().block()
        );
    }

    private GetAllTrainingEmplResponse createResponse(Set<Training> trainings){
        return GetAllTrainingEmplResponse.builder()
                .trainingList(trainings)
                .build();
    }
}
