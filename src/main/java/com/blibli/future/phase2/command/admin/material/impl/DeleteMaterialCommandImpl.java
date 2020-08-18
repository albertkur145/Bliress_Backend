package com.blibli.future.phase2.command.admin.material.impl;

import com.blibli.future.phase2.command.admin.material.DeleteMaterialCommand;
import com.blibli.future.phase2.model.command.admin.material.DeleteMaterialRequest;
import com.blibli.future.phase2.model.response.admin.material.DeleteMaterialResponse;
import com.blibli.future.phase2.repository.TrainingMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeleteMaterialCommandImpl implements DeleteMaterialCommand {
    @Autowired
    private TrainingMaterialRepository trainingMaterialRepository;

    @Override
    public Mono<DeleteMaterialResponse> execute(DeleteMaterialRequest request) {
        return Mono.from(trainingMaterialRepository.findById(request.getMaterialId()))
                .flatMap(material -> trainingMaterialRepository.delete(material))
                .thenReturn(createResponse(HttpStatus.OK, "Material has been deleted"))
                .onErrorReturn(createResponse(HttpStatus.BAD_REQUEST, "Material does not exist"));
    }

    private DeleteMaterialResponse createResponse(HttpStatus status, String message){
        return DeleteMaterialResponse.builder()
                .status(status)
                .message(message)
                .build();
    }


}
