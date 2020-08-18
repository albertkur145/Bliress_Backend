package com.blibli.future.phase2.command.admin.batch.impl;

import com.blibli.future.phase2.command.admin.batch.DeleteBatchCommand;
import com.blibli.future.phase2.model.response.admin.batch.DeleteBatchResponse;
import com.blibli.future.phase2.repository.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeleteBatchCommandImpl implements DeleteBatchCommand {

    @Autowired
    private BatchRepository batchRepository;

    @Override
    public Mono<DeleteBatchResponse> execute(String request) {
        return Mono.from(batchRepository.findById(request))
                .switchIfEmpty(Mono.error(NullPointerException::new))
                .flatMap(batch -> batchRepository.delete(batch))
                .thenReturn(createResponse(HttpStatus.OK, "Batch has been deleted"))
                .onErrorReturn(createResponse(HttpStatus.BAD_REQUEST, "Batch does not exist"));
    }

    private DeleteBatchResponse createResponse(HttpStatus status, String message){
        return DeleteBatchResponse.builder()
                .status(status)
                .message(message)
                .build();
    }
}
