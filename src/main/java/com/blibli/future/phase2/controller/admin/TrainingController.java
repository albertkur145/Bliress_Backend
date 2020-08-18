package com.blibli.future.phase2.controller.admin;

import com.blibli.future.phase2.command.admin.training.*;
import com.blibli.future.phase2.command.employee.training.CheckAttendanceEmplCommand;
import com.blibli.future.phase2.command.employee.training.GetAllTrainingEmplCommand;
import com.blibli.future.phase2.command.employee.training.GetByIdTrainingEmplCommand;
import com.blibli.future.phase2.command.employee.training.SubmitAttendanceTrainingCommand;
import com.blibli.future.phase2.component.AuthenticatedUserProvider;
import com.blibli.future.phase2.controller.ApiPath;
import com.blibli.future.phase2.entity.Training;
import com.blibli.future.phase2.model.command.admin.training.*;
import com.blibli.future.phase2.model.command.user.training.CheckAttendanceEmplRequest;
import com.blibli.future.phase2.model.command.user.training.SubmitAttendanceTrainingRequest;
import com.blibli.future.phase2.model.response.admin.training.*;
import com.blibli.future.phase2.model.response.user.training.CheckAttendanceEmplResponse;
import com.blibli.future.phase2.model.response.user.training.GetAllTrainingEmplResponse;
import com.blibli.future.phase2.model.response.user.training.SubmitAttendanceTrainingResponse;
import com.blibli.oss.command.CommandExecutor;
import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Api
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class TrainingController {
    @Autowired
    private CommandExecutor commandExecutor;

    @Autowired
    private AuthenticatedUserProvider authenticatedUserProvider;

    @PostMapping(ApiPath.ADMIN_TRAINING_CREATE)
    public Mono<Response<CreateTrainingResponse>> adminCreateTraining(@RequestBody CreateTrainingRequest request){
        return commandExecutor.execute(CreateTrainingCommand.class, request)
                .map(response -> ResponseHelper.status(response.getStatus(), response))
                .subscribeOn(Schedulers.parallel());
    }

    @GetMapping(ApiPath.ADMIN_TRAINING_GET_ALL)
    public Mono<Response<GetAllTrainingResponse>> adminGetAllTraining(@RequestParam String batchId){
        return commandExecutor.execute(GetAllTrainingCommand.class, batchId)
                .map(response -> ResponseHelper.ok(response))
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(ApiPath.ADMIN_TRAINING_GET_BY_ID)
    public Mono<Response<Training>> adminGetByIdTraining(@RequestParam String batchId, @RequestParam String training){
        return commandExecutor.execute(GetByIdTrainingCommand.class,
                GetByIdTrainingRequest.builder().batchId(batchId).training(training).build()
        )
                .map(response -> ResponseHelper.ok(response))
                .subscribeOn(Schedulers.elastic());
    }

    @PutMapping(ApiPath.ADMIN_TRAINING_UPDATE)
    public Mono<Response<UpdateTrainingResponse>> adminUpdateTraining(@RequestBody UpdateTrainingRequest request){
        return commandExecutor.execute(UpdateTrainingCommand.class, request)
                .map(response -> ResponseHelper.status(response.getStatus(), response))
                .subscribeOn(Schedulers.elastic());
    }

    @DeleteMapping(ApiPath.ADMIN_TRAINING_DELETE)
    public Mono<Response<DeleteTrainingResponse>> adminDeleteTraining(@RequestParam String batchId, @RequestParam String training){
        return commandExecutor.execute(DeleteTrainingCommand.class, DeleteTrainingRequest.builder()
                .batchId(batchId).training(training).build()
        )
                .map(response -> ResponseHelper.status(response.getStatus(), response))
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(ApiPath.ADMIN_TRAINING_GET_ATTENDANCE)
    public Mono<Response<CheckAttendanceResponse>> adminGetAttendance(@RequestParam String batchId, @RequestParam String training){
        return commandExecutor.execute(CheckAttendanceCommand.class, CheckAttendanceRequest.builder()
                .batchId(batchId)
                .training(training)
                .build())
            .map(response -> ResponseHelper.ok(response))
            .subscribeOn(Schedulers.elastic());

    }

    @GetMapping(ApiPath.TRAINER_TRAINING_GET_ALL)
    public Mono<Response<GetAllByTrainerIdResponse>> trainerGetAllTraining(@RequestParam String trainerId){
        return commandExecutor.execute(GetAllByTrainerIdTrainingCommand.class, trainerId)
                .map(response -> ResponseHelper.ok(response))
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(ApiPath.TRAINER_TRAINING_GET_BY_ID)
    public Mono<Response<Training>> trainerGetByIdTraining(@RequestParam String batchId, @RequestParam String training){
        return commandExecutor.execute(GetByIdTrainingCommand.class, GetByIdTrainingRequest.builder()
                .batchId(batchId).training(training).build()
        )
                .map(response -> ResponseHelper.ok(response))
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(ApiPath.EMPLOYEE_TRAINING_GET_ALL)
    public Mono<Response<GetAllTrainingEmplResponse>> employeeGetAllTraining(@RequestHeader HttpHeaders headers){
        return commandExecutor.execute(GetAllTrainingEmplCommand.class, authenticatedUserProvider.getTokenFromHeader(headers))
                .map(response -> ResponseHelper.ok(response))
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(ApiPath.EMPLOYEE_TRAINING_GET_BY_ID)
    public Mono<Response<Training>> employeeGetByStage(@RequestHeader HttpHeaders headers){
        return commandExecutor.execute(GetByIdTrainingEmplCommand.class, authenticatedUserProvider.getTokenFromHeader(headers))
                .map(response -> ResponseHelper.ok(response))
                .subscribeOn(Schedulers.elastic());
    }

    @PostMapping(ApiPath.EMPLOYEE_TRAINING_SUBMIT_ATTENDANCE)
    public Mono<Response<SubmitAttendanceTrainingResponse>> submitAttendance(
            @RequestHeader HttpHeaders headers,
            @RequestParam String employeeId,
            @RequestParam String training
    ) {
        return commandExecutor.execute(SubmitAttendanceTrainingCommand.class, SubmitAttendanceTrainingRequest.builder()
                    .token(authenticatedUserProvider.getTokenFromHeader(headers))
                    .employeeId(employeeId)
                    .training(training)
                    .build())
                .map(response -> ResponseHelper.status(response.getStatus(), response))
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(ApiPath.EMPLOYEE_TRAINING_CHECK_ATTENDANCE)
    public Mono<Response<CheckAttendanceEmplResponse>> employeeCheckAttendance(
            @RequestHeader HttpHeaders headers,
            @RequestParam String training
    ){
        return commandExecutor.execute(CheckAttendanceEmplCommand.class, CheckAttendanceEmplRequest.builder()
                .token(authenticatedUserProvider.getTokenFromHeader(headers))
                .training(training)
                .build())
            .map(response -> ResponseHelper.ok(response))
            .subscribeOn(Schedulers.elastic());
    }
}
