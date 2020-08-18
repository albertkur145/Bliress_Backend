package com.blibli.future.phase2.repository;

import com.blibli.future.phase2.entity.TrainingMaterial;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TrainingMaterialRepository extends ReactiveMongoRepository<TrainingMaterial, String> {
    Flux<TrainingMaterial> findAllByBatchIdAndStage(String batchId, Integer stage);
}
