package com.blibli.future.phase2.command.admin.file.impl;

import com.blibli.future.phase2.command.admin.file.MaterialFileUploadCommand;
import com.blibli.future.phase2.model.command.admin.material.UploadMaterialFileRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Service
public class MaterialFileUploadCommandImpl implements MaterialFileUploadCommand {
    @Value("file.upload-dir")
    private String pathFolder;

    @Override
    public Mono<String> execute(FilePart request) {
        return Mono.fromCallable(() -> createTempFile("/cobacoba", request.filename()))
                .map(path -> {
                    try {
                        return writeFile(request, path);
                    } catch (IOException e) {
                        return createResponse("ERROR");
                    }
                });
    }

    private Path createTempFile(String pathFile, String fileName) throws IOException {
        return Files.createTempFile(pathFolder + pathFile, fileName);
    }

    private String writeFile(FilePart request, Path tempFile) throws IOException {
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(tempFile, StandardOpenOption.WRITE);
        DataBufferUtils.write(request.content(), channel, 0)
                .doOnComplete(() -> System.out.println("SUCCESS"))
                .subscribeOn(Schedulers.elastic());
        return createResponse("SUCCESS");
    }

    private String createResponse(String message){
        return message;
    }
}
