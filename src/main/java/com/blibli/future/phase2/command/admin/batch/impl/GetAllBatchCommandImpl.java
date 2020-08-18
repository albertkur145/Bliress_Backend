package com.blibli.future.phase2.command.admin.batch.impl;

import com.blibli.future.phase2.command.admin.batch.GetAllBatchCommand;
import com.blibli.future.phase2.model.command.BlankRequest;
import com.blibli.future.phase2.model.response.admin.batch.GetAllBatchResponse;
import com.blibli.future.phase2.repository.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashSet;

@Service
public class GetAllBatchCommandImpl implements GetAllBatchCommand {
    @Autowired
    private BatchRepository batchRepository;

    @Override
    public Mono<GetAllBatchResponse> execute(BlankRequest request) {
        return Mono.fromCallable(() -> createResponse());
    }

    private GetAllBatchResponse createResponse(){
        return GetAllBatchResponse.builder()
                .batchList(
                        new HashSet<>(Collections.unmodifiableList(
                                batchRepository.findAll().collectList().block())
                        )
                )
                .build();
    }
}
