package com.blibli.future.phase2.controller.auth;

import com.blibli.future.phase2.command.auth.ChangePasswordCommand;
import com.blibli.future.phase2.command.auth.CreateUserCommand;
import com.blibli.future.phase2.command.auth.LoginCommand;
import com.blibli.future.phase2.command.auth.ResetPasswordCommand;
import com.blibli.future.phase2.controller.ApiPath;
import com.blibli.future.phase2.model.command.auth.ChangePasswordRequest;
import com.blibli.future.phase2.model.command.auth.CreateUserRequest;
import com.blibli.future.phase2.model.command.auth.LoginRequest;
import com.blibli.future.phase2.model.response.auth.ChangePasswordResponse;
import com.blibli.future.phase2.model.response.auth.CreateUserResponse;
import com.blibli.future.phase2.model.response.auth.LoginResponse;
import com.blibli.future.phase2.model.response.auth.ResetPasswordResponse;
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

@Api
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    @Autowired
    private CommandExecutor commandExecutor;

    @PostMapping(ApiPath.AUTH_REGISTER)
    public Mono<Response<CreateUserResponse>> registerUser(@RequestBody CreateUserRequest request){
        return commandExecutor.execute(CreateUserCommand.class, request)
                .map(response -> ResponseHelper.ok(response))
                .subscribeOn(Schedulers.elastic());
    }

    @PostMapping(ApiPath.AUTH_LOGIN)
    public Mono<Response<LoginResponse>> login(@RequestBody LoginRequest request){
        return commandExecutor.execute(LoginCommand.class, request)
                .map(response -> ((response.getToken() == null) ? ResponseHelper.status(HttpStatus.BAD_REQUEST, response) : ResponseHelper.ok(response)))
                .subscribeOn(Schedulers.elastic());
    }

    @PutMapping(ApiPath.ADMIN_TRAINER_RESET_PASSWORD)
    public Mono<Response<ResetPasswordResponse>> resetTrainerPassword(@RequestParam String id){
        return commandExecutor.execute(ResetPasswordCommand.class, id)
                .map(response -> ResponseHelper.status(response.getStatus(), response))
                .subscribeOn(Schedulers.elastic());
    }

    @PutMapping(ApiPath.ADMIN_EMPLOYEE_RESET_PASSWORD)
    public Mono<Response<ResetPasswordResponse>> resetEmployeePassword(@RequestParam String id){
        return commandExecutor.execute(ResetPasswordCommand.class, id)
                .map(response -> ResponseHelper.status(response.getStatus(), response))
                .subscribeOn(Schedulers.elastic());
    }

    @PutMapping(ApiPath.AUTH_CHANGE_PASSWORD)
    public Mono<Response<ChangePasswordResponse>> changePassword(@RequestBody ChangePasswordRequest request){
        return commandExecutor.execute(ChangePasswordCommand.class, request)
                .map(response -> ResponseHelper.status(response.getStatus(), response))
                .subscribeOn(Schedulers.elastic());
    }
}
