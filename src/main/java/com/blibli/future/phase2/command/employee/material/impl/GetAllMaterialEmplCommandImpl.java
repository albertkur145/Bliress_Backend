package com.blibli.future.phase2.command.employee.material.impl;

import com.blibli.future.phase2.command.employee.material.GetAllMaterialEmplCommand;
import com.blibli.future.phase2.component.AuthenticatedUserProvider;
import com.blibli.future.phase2.entity.TrainingMaterial;
import com.blibli.future.phase2.model.response.user.material.GetAllMaterialEmplResponse;
import com.blibli.future.phase2.repository.TrainingMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class GetAllMaterialEmplCommandImpl implements GetAllMaterialEmplCommand {
    @Autowired
    private TrainingMaterialRepository trainingMaterialRepository;

    @Autowired
    private AuthenticatedUserProvider authenticatedUserProvider;

    @Override
    public Mono<GetAllMaterialEmplResponse> execute(String request) {
        return Mono.fromCallable(() -> getAllMaterialForUser(request))
                .map(materials -> createResponse(materials));
    }

    private Set<TrainingMaterial> getAllMaterialForUser(String token){
        return new HashSet<>(Collections.unmodifiableList(
                trainingMaterialRepository.findAllByBatchIdAndStage(
                        (String) authenticatedUserProvider.getValueFromUserData(token, "batch"),
                        (Integer) authenticatedUserProvider.getValueFromUserData(token, "stage")
                ).collectList().block()
        ));
    }

    private GetAllMaterialEmplResponse createResponse(Set<TrainingMaterial> materials){
        return GetAllMaterialEmplResponse.builder()
                .materialList(materials)
                .build();
    }
}
