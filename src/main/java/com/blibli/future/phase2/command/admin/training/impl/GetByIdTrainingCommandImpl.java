package com.blibli.future.phase2.command.admin.training.impl;

import com.blibli.future.phase2.command.admin.training.GetByIdTrainingCommand;
import com.blibli.future.phase2.entity.Training;
import com.blibli.future.phase2.model.command.admin.training.GetByIdTrainingRequest;
import com.blibli.future.phase2.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetByIdTrainingCommandImpl implements GetByIdTrainingCommand {
    @Autowired
    private TrainingRepository trainingRepository;

    @Override
    public Mono<Training> execute(GetByIdTrainingRequest request) {
        return trainingRepository.findByBatchIdAndStage(
                request.getBatchId(),
                Integer.parseInt(request.getTraining())
        )
                .switchIfEmpty(Mono.just(Training.builder().build()));
    }
}
