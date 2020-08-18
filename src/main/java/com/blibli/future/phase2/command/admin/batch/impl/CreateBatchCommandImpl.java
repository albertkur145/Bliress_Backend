package com.blibli.future.phase2.command.admin.batch.impl;

import com.blibli.future.phase2.command.admin.batch.CreateBatchCommand;
import com.blibli.future.phase2.entity.Batch;
import com.blibli.future.phase2.model.command.admin.batch.CreateBatchRequest;
import com.blibli.future.phase2.model.response.admin.batch.CreateBatchResponse;
import com.blibli.future.phase2.repository.BatchRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class CreateBatchCommandImpl implements CreateBatchCommand {
    @Autowired
    private BatchRepository batchRepository;

    @Override
    public Mono<CreateBatchResponse> execute(CreateBatchRequest request){
        return Mono.from(checkIfBatchIsExist(request))
                .flatMap(result -> (result) ? Mono.just(null) : Mono.just(createBatch(request)))
                .flatMap(batch -> (batch == null) ? Mono.just(null) : batchRepository.save(batch))
                .map(batch -> createResponse(HttpStatus.ACCEPTED, "Batch has been created"))
                .onErrorReturn(createResponse(HttpStatus.BAD_REQUEST, "Batch cannot be created"));
    }

    public Mono<Boolean> checkIfBatchIsExist(CreateBatchRequest request){
        return Mono.from(batchRepository.findByMonthAndYear(request.getBatch(), request.getYear()))
                .switchIfEmpty(Mono.just(Batch.builder().build()))
                .map(batch -> batch.getBatchId() != null);
    }

    private Batch createBatch(CreateBatchRequest request){
        String monthText = getMonth(Integer.parseInt(request.getBatch()));
        String year = request.getYear();
        String batchId = monthText.substring(0, 3) + "-" + year;

        Batch newBatch = Batch.builder()
                .batchId(batchId)
                .batchName(monthText + " - " + year)
                .month(request.getBatch())
                .year(request.getYear())
                .build();

        return newBatch;
    }

    private String getMonth(Integer index){
        String[] monthText = new String[]{
                "JANUARY", "FEBRUARY", "MARCH", "APRIL",
                "MAY", "JUNE", "JULY", "AUGUST",
                "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER",
        };
        return monthText[index - 1];
    }

    private CreateBatchResponse createResponse(HttpStatus status, String message){
        return CreateBatchResponse.builder()
                .status(status)
                .message(message)
                .build();
    }
}
