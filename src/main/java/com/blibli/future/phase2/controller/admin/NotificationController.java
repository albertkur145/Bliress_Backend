package com.blibli.future.phase2.controller.admin;

import com.blibli.future.phase2.command.admin.notification.CreateNotificationCommand;
import com.blibli.future.phase2.command.admin.notification.GetAllNotificationCommand;
import com.blibli.future.phase2.command.employee.notification.CheckNotificationEmplCommand;
import com.blibli.future.phase2.command.employee.notification.GetAllNotificationEmplCommand;
import com.blibli.future.phase2.command.employee.notification.ReadNotificationEmplCommand;
import com.blibli.future.phase2.component.AuthenticatedUserProvider;
import com.blibli.future.phase2.controller.ApiPath;
import com.blibli.future.phase2.model.command.BlankRequest;
import com.blibli.future.phase2.model.command.admin.notification.CreateNotificationRequest;
import com.blibli.future.phase2.model.response.admin.notification.CreateNotificationResponse;
import com.blibli.future.phase2.model.response.admin.notification.GetAllNotificationResponse;
import com.blibli.future.phase2.model.response.admin.notification.ReadNotificationEmplResponse;
import com.blibli.future.phase2.model.response.user.notification.CheckNotificationEmplResponse;
import com.blibli.future.phase2.model.response.user.notification.GetAllNotificationEmplResponse;
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
public class NotificationController {
    @Autowired
    private CommandExecutor commandExecutor;

    @Autowired
    private AuthenticatedUserProvider authenticatedUserProvider;

    @PostMapping(ApiPath.ADMIN_NOTIFICATION_CREATE)
    public Mono<Response<CreateNotificationResponse>> adminCreateNotification(@RequestBody CreateNotificationRequest request){
        return commandExecutor.execute(CreateNotificationCommand.class, request)
                .map(response -> ResponseHelper.status(response.getStatus(), response))
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(ApiPath.ADMIN_NOTIFICATION_GET_ALL)
    public Mono<Response<GetAllNotificationResponse>> adminGetAllNotification(){
        return commandExecutor.execute(GetAllNotificationCommand.class, BlankRequest.builder().build())
                .map(response -> ResponseHelper.ok(response))
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(ApiPath.EMPLOYEE_NOTIFICATION_GET_ALL)
    public Mono<Response<GetAllNotificationEmplResponse>> employeeGetAllNotification(@RequestHeader HttpHeaders headers){
        return commandExecutor.execute(GetAllNotificationEmplCommand.class, authenticatedUserProvider.getTokenFromHeader(headers))
                .map(response -> ResponseHelper.ok(response))
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(ApiPath.EMPLOYEE_NOTIFICATION_CHECK)
    public Mono<Response<CheckNotificationEmplResponse>> employeeCheckNotification(@RequestHeader HttpHeaders headers){
        return commandExecutor.execute(CheckNotificationEmplCommand.class, authenticatedUserProvider.getTokenFromHeader(headers))
                .map(response -> ResponseHelper.ok(response))
                .subscribeOn(Schedulers.elastic());
    }

    @PostMapping(ApiPath.EMPLOYEE_NOTIFICATION_READ)
    public Mono<Response<ReadNotificationEmplResponse>> employeeReadNotification(@RequestHeader HttpHeaders headers){
        return commandExecutor.execute(ReadNotificationEmplCommand.class, authenticatedUserProvider.getTokenFromHeader(headers))
                .map(response -> ResponseHelper.status(response.getStatus(), response))
                .subscribeOn(Schedulers.elastic());
    }
}
