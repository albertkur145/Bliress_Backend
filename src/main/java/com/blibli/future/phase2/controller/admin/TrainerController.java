package com.blibli.future.phase2.controller.admin;

import com.blibli.future.phase2.command.admin.trainer.*;
import com.blibli.future.phase2.controller.ApiPath;
import com.blibli.future.phase2.entity.User;
import com.blibli.future.phase2.model.command.BlankRequest;
import com.blibli.future.phase2.model.command.admin.trainer.CreateTrainerRequest;
import com.blibli.future.phase2.model.command.admin.trainer.UpdateTrainerRequest;
import com.blibli.future.phase2.model.response.admin.trainer.CreateTrainerResponse;
import com.blibli.future.phase2.model.response.admin.trainer.DeleteTrainerResponse;
import com.blibli.future.phase2.model.response.admin.trainer.GetAllTrainerResponse;
import com.blibli.future.phase2.model.response.admin.trainer.UpdateTrainerResponse;
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
public class TrainerController {
    @Autowired
    private CommandExecutor commandExecutor;

    @PostMapping(ApiPath.ADMIN_TRAINER_CREATE)
    public Mono<Response<CreateTrainerResponse>> createTrainer(@RequestBody CreateTrainerRequest request){
        return commandExecutor.execute(CreateTrainerCommand.class, request)
                .map(response -> ResponseHelper.status(response.getStatus(), response))
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(ApiPath.ADMIN_TRAINER_GET_ALL)
    public Mono<Response<GetAllTrainerResponse>> getAllTrainer(){
        return commandExecutor.execute(GetAllTrainerCommand.class, BlankRequest.builder().build())
                .map(response -> ResponseHelper.ok(response))
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(ApiPath.ADMIN_TRAINER_GET_BY_ID)
    public Mono<Response<User>> getByIdTrainer(@RequestParam String id){
        return commandExecutor.execute(GetByIdTrainerCommand.class, id)
                .map(response -> ResponseHelper.ok(response))
                .subscribeOn(Schedulers.elastic());
    }

    @PutMapping(ApiPath.ADMIN_TRAINER_UPDATE)
    public Mono<Response<UpdateTrainerResponse>> updateTrainer(@RequestBody UpdateTrainerRequest request){
        return commandExecutor.execute(UpdateTrainerCommand.class, request)
                .map(response -> ResponseHelper.status(response.getStatus(), response))
                .subscribeOn(Schedulers.elastic());
    }

    @DeleteMapping(ApiPath.ADMIN_TRAINER_DELETE)
    public Mono<Response<DeleteTrainerResponse>> deleteTrainer(@RequestParam String id){
        return commandExecutor.execute(DeleteTrainerCommand.class, id)
                .map(response -> ResponseHelper.status(response.getStatus(), response))
                .subscribeOn(Schedulers.elastic());
    }
}
