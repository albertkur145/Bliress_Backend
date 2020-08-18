package com.blibli.future.phase2.controller.admin;

import com.blibli.future.phase2.command.admin.material.CreateMaterialCommand;
import com.blibli.future.phase2.command.admin.material.DeleteMaterialCommand;
import com.blibli.future.phase2.command.admin.material.GetAllMaterialCommand;
import com.blibli.future.phase2.command.employee.material.GetAllMaterialEmplCommand;
import com.blibli.future.phase2.component.AuthenticatedUserProvider;
import com.blibli.future.phase2.controller.ApiPath;
import com.blibli.future.phase2.model.command.admin.material.CreateMaterialRequest;
import com.blibli.future.phase2.model.command.admin.material.DeleteMaterialRequest;
import com.blibli.future.phase2.model.command.admin.material.GetAllMaterialRequest;
import com.blibli.future.phase2.model.command.user.material.GetAllMaterialEmplRequest;
import com.blibli.future.phase2.model.response.admin.material.CreateMaterialResponse;
import com.blibli.future.phase2.model.response.admin.material.DeleteMaterialResponse;
import com.blibli.future.phase2.model.response.admin.material.GetAllMaterialResponse;
import com.blibli.future.phase2.model.response.user.material.GetAllMaterialEmplResponse;
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
public class MaterialController {
    @Autowired
    private CommandExecutor commandExecutor;

    @Autowired
    private AuthenticatedUserProvider authenticatedUserProvider;

    @PostMapping(ApiPath.ADMIN_MATERIAL_CREATE)
    public Mono<Response<CreateMaterialResponse>> adminCreateMaterial(@RequestBody CreateMaterialRequest request){
        return commandExecutor.execute(CreateMaterialCommand.class, request)
                .map(response -> ResponseHelper.status(response.getStatus(), response))
                .subscribeOn(Schedulers.elastic());

    }

    @GetMapping(ApiPath.ADMIN_MATERIAL_GET_ALL)
    public Mono<Response<GetAllMaterialResponse>> adminGetAllMaterial(@RequestParam String batchId, @RequestParam String training){
        return commandExecutor.execute(GetAllMaterialCommand.class, GetAllMaterialRequest.builder()
                .batchId(batchId).training(training).build()
        )
                .map(response -> ResponseHelper.ok(response))
                .subscribeOn(Schedulers.elastic());
    }

    @DeleteMapping(ApiPath.ADMIN_MATERIAL_DELETE)
    public Mono<Response<DeleteMaterialResponse>> adminDeleteMaterial(@RequestParam String materialId, @RequestParam String batchId, @RequestParam String training){
        return commandExecutor.execute(DeleteMaterialCommand.class, DeleteMaterialRequest.builder()
                .batchId(batchId).training(training).materialId(materialId).build()
        )
                .map(response -> ResponseHelper.status(response.getStatus(), response))
                .subscribeOn(Schedulers.elastic());
    }

    @PostMapping(ApiPath.TRAINER_MATERIAL_CREATE)
    public Mono<Response<CreateMaterialResponse>> trainerCreateMaterial(@RequestBody CreateMaterialRequest request){
        return commandExecutor.execute(CreateMaterialCommand.class, request)
                .map(response -> ResponseHelper.status(response.getStatus(), response))
                .subscribeOn(Schedulers.elastic());

    }

    @DeleteMapping(ApiPath.TRAINER_MATERIAL_DELETE)
    public Mono<Response<DeleteMaterialResponse>> trainerDeleteMaterial(@RequestParam String materialId, @RequestParam String batchId, @RequestParam String training){
        return commandExecutor.execute(DeleteMaterialCommand.class, DeleteMaterialRequest.builder()
                .batchId(batchId).training(training).materialId(materialId).build()
        )
                .map(response -> ResponseHelper.status(response.getStatus(), response))
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(ApiPath.EMPLOYEE_MATERIAL_GET_ALL)
    public Mono<Response<GetAllMaterialEmplResponse>> employeeGetAll(@RequestHeader HttpHeaders headers){
        return commandExecutor.execute(GetAllMaterialEmplCommand.class, authenticatedUserProvider.getTokenFromHeader(headers))
                .map(response -> ResponseHelper.ok(response))
                .subscribeOn(Schedulers.elastic());
    }
}
