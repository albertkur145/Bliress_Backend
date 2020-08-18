package com.blibli.future.phase2.command.admin.batch.impl;

import com.blibli.future.phase2.command.admin.batch.GetByIdBatchCommand;
import com.blibli.future.phase2.entity.Batch;
import com.blibli.future.phase2.model.response.admin.batch.GetByIdBatchResponse;
import com.blibli.future.phase2.repository.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetByIdBatchCommandImpl implements GetByIdBatchCommand {
    @Autowired
    private BatchRepository batchRepository;

    @Override
    public Mono<Batch> execute(String request) {
        return batchRepository.findById(request)
                .switchIfEmpty(Mono.just(Batch.builder().build()));
    }
}
