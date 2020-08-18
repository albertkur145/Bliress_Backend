package com.blibli.future.phase2.command.admin.material.impl;

import com.blibli.future.phase2.command.admin.material.GetAllMaterialCommand;
import com.blibli.future.phase2.entity.TrainingMaterial;
import com.blibli.future.phase2.model.command.admin.material.GetAllMaterialRequest;
import com.blibli.future.phase2.model.response.admin.material.GetAllMaterialResponse;
import com.blibli.future.phase2.repository.TrainingMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class GetAllMaterialCommandImpl implements GetAllMaterialCommand {
    @Autowired
    private TrainingMaterialRepository trainingMaterialRepository;

    @Override
    public Mono<GetAllMaterialResponse> execute(GetAllMaterialRequest request) {
        return Mono.fromCallable(() -> getAllTrainingMaterial(request))
                .map(materials -> setDateMaterial(materials))
                .map(materials -> createResponse(materials));
    }

    private Set<TrainingMaterial> getAllTrainingMaterial(GetAllMaterialRequest request){
        return new HashSet<>(Collections.unmodifiableList(
                trainingMaterialRepository.findAllByBatchIdAndStage(
                        request.getBatchId(), Integer.parseInt(request.getTraining())
                ).collectList().block()
        ));
    }

    private GetAllMaterialResponse createResponse(Set<TrainingMaterial> materials){
        return GetAllMaterialResponse.builder()
                .materialList(materials)
                .build();
    }

    public Set<TrainingMaterial> setDateMaterial(Set<TrainingMaterial> trainings){
        trainings.forEach(material -> {
            if (material.getTestExist().equals(Boolean.TRUE)){
                Instant dateInstant = LocalDate.parse(material.getTestAvailable(), DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                        .atStartOfDay(ZoneId.systemDefault()).toInstant();
                LocalDateTime date = LocalDateTime.ofInstant(dateInstant, ZoneId.systemDefault());
                material.setTestAvailable(date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));

                dateInstant = LocalDate.parse(material.getTestClosed(), DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                        .atStartOfDay(ZoneId.systemDefault()).toInstant();
                date = LocalDateTime.ofInstant(dateInstant, ZoneId.systemDefault());
                material.setTestClosed(date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
            }
        });

        return trainings;
    }
}
