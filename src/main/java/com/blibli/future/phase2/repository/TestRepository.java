package com.blibli.future.phase2.repository;

import com.blibli.future.phase2.entity.Test;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface TestRepository extends ReactiveMongoRepository<Test, String> {
    Mono<Test> findByBatchIdAndStageAndAndMaterialId(String batchId, Integer stage, String materialId);
}
