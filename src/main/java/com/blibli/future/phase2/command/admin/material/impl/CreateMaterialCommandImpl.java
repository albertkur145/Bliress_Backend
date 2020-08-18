package com.blibli.future.phase2.command.admin.material.impl;

import com.blibli.future.phase2.command.admin.material.CreateMaterialCommand;
import com.blibli.future.phase2.entity.TrainingMaterial;
import com.blibli.future.phase2.model.command.admin.material.CreateMaterialRequest;
import com.blibli.future.phase2.model.response.admin.material.CreateMaterialResponse;
import com.blibli.future.phase2.repository.TrainingMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class CreateMaterialCommandImpl implements CreateMaterialCommand {
    @Autowired
    private TrainingMaterialRepository trainingMaterialRepository;

    @Override
    public Mono<CreateMaterialResponse> execute(CreateMaterialRequest request) {
        return Mono.fromCallable(() -> createMaterial(request))
                .flatMap(material -> trainingMaterialRepository.save(material))
                .map(material -> createResponse(HttpStatus.ACCEPTED, "Material has been created", material.getMaterialId()));
    }

    private TrainingMaterial createMaterial(CreateMaterialRequest request){
        return TrainingMaterial.builder()
                .materialId(UUID.randomUUID().toString())
                .batchId(request.getBatchId())
                .stage(Integer.parseInt(request.getTraining()))
                .materialName(request.getMaterialName())
                .testExist(Boolean.FALSE)
                .build();
    }

    private CreateMaterialResponse createResponse(HttpStatus status, String message, String materialId){
        return CreateMaterialResponse.builder()
                .status(status)
                .message(message)
                .materialId(materialId)
                .build();
    }
}
