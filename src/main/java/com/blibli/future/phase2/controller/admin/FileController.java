package com.blibli.future.phase2.controller.admin;

import com.blibli.future.phase2.command.admin.file.MaterialFileUploadCommand;
import com.blibli.future.phase2.controller.ApiPath;
import com.blibli.future.phase2.model.command.admin.material.UploadMaterialFileRequest;
import com.blibli.oss.command.CommandExecutor;
import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.nio.file.Paths;

@Api
@RestController
@RequestMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public class FileController {
    @Autowired
    private CommandExecutor commandExecutor;

    @Value("file.upload-dir")
    private String pathFolder;

    @PostMapping(ApiPath.ADMIN_MATERIAL_FILE_UPLOAD)
    public Mono<Response<String>> uploadMaterialFile(@RequestPart("content") Flux<FilePart> file){
//        return commandExecutor.execute(MaterialFileUploadCommand.class, file)
//                .map(response -> ResponseHelper.ok(response))
//                .subscribeOn(Schedulers.elastic());

        return file.flatMap(it -> it.transferTo(Paths.get(pathFolder + "/" + it.filename())))
                .then(Mono.fromCallable(() -> ResponseHelper.ok("SUCCESS")))
                .subscribeOn(Schedulers.elastic());
    }
}
