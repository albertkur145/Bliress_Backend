package com.blibli.future.phase2.repository;

import com.blibli.future.phase2.entity.TrainingAttendance;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TrainingAttendanceRepository extends ReactiveMongoRepository<TrainingAttendance, String> {
    Mono<TrainingAttendance> findByEmployeeId(String employeeId);

    Mono<Boolean> existsByEmployeeIdAndBatchIdAndStageAndStatus(String employeeId, String batchId, Integer stage, Boolean status);

    Flux<TrainingAttendance> findAllByBatchIdAndStage(String batchId, Integer stage);
}
