package com.blibli.future.phase2.command.admin.batch.impl;

import com.blibli.future.phase2.command.admin.batch.GetAllBatchTrainingCommand;
import com.blibli.future.phase2.entity.Batch;
import com.blibli.future.phase2.entity.Training;
import com.blibli.future.phase2.model.command.BlankRequest;
import com.blibli.future.phase2.model.response.admin.batch.BatchTrainingResponse;
import com.blibli.future.phase2.repository.BatchRepository;
import com.blibli.future.phase2.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

@Service
public class GetAllBatchTrainingCommandImpl implements GetAllBatchTrainingCommand {
    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private TrainingRepository trainingRepository;

    @Override
    public Mono<List<BatchTrainingResponse>> execute(BlankRequest request) {
        return Mono.fromCallable(() -> getData());
    }

    private List<BatchTrainingResponse> getData(){
        return batchRepository.findAll()
                .map(batch -> createData(batch))
                .collectList().block();
    }

    private BatchTrainingResponse createData(Batch batch){
        List<Training> trainings = trainingRepository.findAllByBatchIdOrderByStageAsc(batch.getBatchId()).collectList().block();

        return BatchTrainingResponse.builder()
                .batch(batch)
                .trainings(trainings)
                .build();
    }
}
