package com.blibli.future.phase2.repository;

import com.blibli.future.phase2.entity.Batch;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

public interface BatchRepository extends ReactiveMongoRepository<Batch, String> {
    Mono<Batch> findByBatchName(String batchName);

    Mono<Batch> findByMonthAndYear(String month, String year);
}
