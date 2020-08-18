package com.blibli.future.phase2.controller.admin;

import com.blibli.future.phase2.command.admin.batch.*;
import com.blibli.future.phase2.controller.ApiPath;
import com.blibli.future.phase2.entity.Batch;
import com.blibli.future.phase2.model.command.BlankRequest;
import com.blibli.future.phase2.model.command.admin.batch.CreateBatchRequest;
import com.blibli.future.phase2.model.response.admin.batch.*;
import com.blibli.oss.command.CommandExecutor;
import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Set;

@Api
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class BatchController {

    @Autowired
    private CommandExecutor commandExecutor;

    @PostMapping(ApiPath.ADMIN_BATCH_CREATE)
    public Mono<Response<CreateBatchResponse>> createBatch(@RequestBody CreateBatchRequest request){
        return commandExecutor.execute(CreateBatchCommand.class, request)
                .map(response -> ResponseHelper.status(response.getStatus(), response))
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(ApiPath.ADMIN_BATCH_GET_ALL)
    public Mono<Response<GetAllBatchResponse>> getAllBatch(){
        return commandExecutor.execute(GetAllBatchCommand.class, BlankRequest.builder().build())
                .map(response -> ResponseHelper.ok(response))
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(ApiPath.ADMIN_BATCH_GET_BY_ID)
    public Mono<Response<Batch>> getById(@RequestParam String batchId){
        return commandExecutor.execute(GetByIdBatchCommand.class, batchId)
                .map(response -> ResponseHelper.ok(response))
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(ApiPath.ADMIN_GET_ALL_BATCH_TRAINING)
    public Mono<Response<List<BatchTrainingResponse>>> getAllBatchTraining(){
        return commandExecutor.execute(GetAllBatchTrainingCommand.class, BlankRequest.builder().build())
                .map(response -> ResponseHelper.ok(response))
                .subscribeOn(Schedulers.elastic());
    }

    @DeleteMapping(ApiPath.ADMIN_BATCH_DELETE)
    public Mono<Response<DeleteBatchResponse>> deleteBatchByBatchName(@RequestParam String batchId){
        return commandExecutor.execute(DeleteBatchCommand.class, batchId)
                .map(response -> ResponseHelper.status(response.getStatus(), response))
                .subscribeOn(Schedulers.elastic());
    }
}
