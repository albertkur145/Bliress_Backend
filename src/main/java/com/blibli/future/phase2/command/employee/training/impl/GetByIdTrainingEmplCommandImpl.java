package com.blibli.future.phase2.command.employee.training.impl;

import com.blibli.future.phase2.command.employee.training.GetByIdTrainingEmplCommand;
import com.blibli.future.phase2.component.AuthenticatedUserProvider;
import com.blibli.future.phase2.entity.Training;
import com.blibli.future.phase2.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetByIdTrainingEmplCommandImpl implements GetByIdTrainingEmplCommand {
    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private AuthenticatedUserProvider authenticatedUserProvider;

    @Override
    public Mono<Training> execute(String request) {
        return trainingRepository.findByBatchIdAndStage(
                (String) authenticatedUserProvider.getValueFromUserData(request, "batch"),
                (Integer) authenticatedUserProvider.getValueFromUserData(request, "stage")
        ).switchIfEmpty(Mono.just(Training.builder().build()));
    }
}
