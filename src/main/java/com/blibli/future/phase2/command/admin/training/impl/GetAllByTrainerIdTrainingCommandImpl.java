package com.blibli.future.phase2.command.admin.training.impl;

import com.blibli.future.phase2.command.admin.training.GetAllByTrainerIdTrainingCommand;
import com.blibli.future.phase2.model.response.admin.training.GetAllByTrainerIdResponse;
import com.blibli.future.phase2.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashSet;

@Service
public class GetAllByTrainerIdTrainingCommandImpl implements GetAllByTrainerIdTrainingCommand {
    @Autowired
    private TrainingRepository trainingRepository;

    @Override
    public Mono<GetAllByTrainerIdResponse> execute(String request) {
        return null;
    }

    public GetAllByTrainerIdResponse createResponse(String request){
        return GetAllByTrainerIdResponse.builder()
                .trainingList(
                        new HashSet<>(Collections.unmodifiableList(
                                trainingRepository.findAllByTrainerIdOrderByBatchIdAsc(request).collectList().block()
                        )))
                .build();
    }
}
