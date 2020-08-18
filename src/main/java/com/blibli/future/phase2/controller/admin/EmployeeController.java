package com.blibli.future.phase2.controller.admin;

import com.blibli.future.phase2.command.admin.employee.*;
import com.blibli.future.phase2.command.employee.employee.GetEmployeeDataCommand;
import com.blibli.future.phase2.component.AuthenticatedUserProvider;
import com.blibli.future.phase2.controller.ApiPath;
import com.blibli.future.phase2.entity.User;
import com.blibli.future.phase2.model.command.BlankRequest;
import com.blibli.future.phase2.model.command.admin.employee.CreateEmployeeRequest;
import com.blibli.future.phase2.model.command.admin.employee.UpdateEmployeeRequest;
import com.blibli.future.phase2.model.response.admin.employee.*;
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
public class EmployeeController {
    @Autowired
    private CommandExecutor commandExecutor;

    @Autowired
    private AuthenticatedUserProvider authenticatedUserProvider;

    @PostMapping(ApiPath.ADMIN_EMPLOYEE_CREATE)
    public Mono<Response<CreateEmployeeResponse>> createEmployee(@RequestBody CreateEmployeeRequest request){
        return commandExecutor.execute(CreateEmployeeCommand.class, request)
                .map(response -> ResponseHelper.status(response.getStatus(), response))
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(ApiPath.ADMIN_EMPLOYEE_GET_ALL)
    public Mono<Response<GetAllEmployeeResponse>> getAllEmployee(){
        return commandExecutor.execute(GetAllEmployeeCommand.class, BlankRequest.builder().build())
                .map(response -> ResponseHelper.ok(response))
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(ApiPath.ADMIN_EMPLOYEE_GET_BY_ID)
    public Mono<Response<User>> getByIdEmployee(@RequestParam String id) {
        return commandExecutor.execute(GetByIdEmployeeCommand.class, id)
                .map(response -> ResponseHelper.ok(response))
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(ApiPath.ADMIN_EMPLOYEE_GET_ALL_BY_BATCH)
    public Mono<Response<GetAllByBatchEmployeeResponse>> getAllByBatchEmployee(@RequestParam String batchId){
        return commandExecutor.execute(GetAllByBatchEmployeeCommand.class, batchId)
                .map(response -> ResponseHelper.ok(response))
                .subscribeOn(Schedulers.elastic());
    }

    @PutMapping(ApiPath.ADMIN_EMPLOYEE_UPDATE)
    public Mono<Response<UpdateEmployeeResponse>> updateEmployee(@RequestBody UpdateEmployeeRequest request){
        return commandExecutor.execute(UpdateEmployeeCommand.class, request)
                .map(response -> ResponseHelper.status(response.getStatus(), response))
                .subscribeOn(Schedulers.elastic());
    }

    @DeleteMapping(ApiPath.ADMIN_EMPLOYEE_DELETE)
    public Mono<Response<DeleteEmployeeResponse>> deleteEmployee(@RequestParam String id){
        return commandExecutor.execute(DeleteEmployeeCommand.class, id)
                .map(response -> ResponseHelper.status(response.getStatus(), response))
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(ApiPath.EMPLOYEE_GET_BY_ID)
    public Mono<Response<User>> getEmployeeData(@RequestHeader HttpHeaders headers){
        return commandExecutor.execute(GetEmployeeDataCommand.class, authenticatedUserProvider.getTokenFromHeader(headers))
                .map(response -> ResponseHelper.ok(response))
                .subscribeOn(Schedulers.elastic());
    }
}
