package com.blibli.future.phase2.controller.admin;

import com.blibli.future.phase2.command.admin.material.GetAllMaterialCommand;
import com.blibli.future.phase2.command.admin.test.CreateTestCommand;
import com.blibli.future.phase2.command.admin.test.GetByIdTestCommand;
import com.blibli.future.phase2.command.admin.test.UpdateTestCommand;
import com.blibli.future.phase2.controller.ApiPath;
import com.blibli.future.phase2.entity.Test;
import com.blibli.future.phase2.model.command.admin.material.GetAllMaterialRequest;
import com.blibli.future.phase2.model.command.admin.test.CreateTestRequest;
import com.blibli.future.phase2.model.command.admin.test.GetByIdTestRequest;
import com.blibli.future.phase2.model.command.admin.test.UpdateTestRequest;
import com.blibli.future.phase2.model.response.admin.material.GetAllMaterialResponse;
import com.blibli.future.phase2.model.response.admin.test.CreateTestResponse;
import com.blibli.future.phase2.model.response.admin.test.UpdateTestResponse;
import com.blibli.oss.command.CommandExecutor;
import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Api
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {
    @Autowired
    private CommandExecutor commandExecutor;

    @PostMapping(ApiPath.ADMIN_TEST_CREATE)
    public Mono<Response<CreateTestResponse>> adminCreateTest(@RequestBody CreateTestRequest request){
        return commandExecutor.execute(CreateTestCommand.class, request)
                .map(response -> ResponseHelper.status(response.getStatus(), response))
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(ApiPath.ADMIN_TEST_GET_QUESTION)
    public Mono<Response<Test>> adminGetByIdTest(@RequestParam String batchId, @RequestParam String training, @RequestParam String materialId){
        return commandExecutor.execute(GetByIdTestCommand.class, GetByIdTestRequest.builder()
                .batchId(batchId).training(training).materialId(materialId).build()
        )
                .map(response -> ResponseHelper.ok(response))
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(ApiPath.ADMIN_TEST_GET_MATERIAL_TEST)
    public Mono<Response<GetAllMaterialResponse>> getAllMaterial(@RequestParam String batchId, @RequestParam String training){
        return commandExecutor.execute(GetAllMaterialCommand.class, GetAllMaterialRequest.builder()
                .batchId(batchId).training(training).build()
        )
                .map(response -> ResponseHelper.ok(response))
                .subscribeOn(Schedulers.elastic());
    }

    @PutMapping(ApiPath.ADMIN_TEST_UPDATE)
    public Mono<Response<UpdateTestResponse>> adminUpdateTest(@RequestBody UpdateTestRequest request){
        return commandExecutor.execute(UpdateTestCommand.class, request)
                .map(response -> ResponseHelper.status(response.getStatus(), response))
                .subscribeOn(Schedulers.elastic());
    }

    @PostMapping(ApiPath.TRAINER_TEST_CREATE)
    public Mono<Response<CreateTestResponse>> trainerCreateTest(@RequestBody CreateTestRequest request){
        return commandExecutor.execute(CreateTestCommand.class, request)
                .map(response -> ResponseHelper.status(response.getStatus(), response))
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(ApiPath.TRAINER_TEST_GET_QUESTION)
    public Mono<Response<Test>> trainerGetByIdTest(@RequestParam String batchId, @RequestParam String training, @RequestParam String materialId){
        return commandExecutor.execute(GetByIdTestCommand.class, GetByIdTestRequest.builder()
                .batchId(batchId).training(training).materialId(materialId).build()
        )
                .map(response -> ResponseHelper.ok(response))
                .subscribeOn(Schedulers.elastic());
    }

    @PutMapping(ApiPath.TRAINER_TEST_UPDATE)
    public Mono<Response<UpdateTestResponse>> trainerUpdateTest(@RequestBody UpdateTestRequest request){
        return commandExecutor.execute(UpdateTestCommand.class, request)
                .map(response -> ResponseHelper.status(response.getStatus(), response))
                .subscribeOn(Schedulers.elastic());
    }
}
