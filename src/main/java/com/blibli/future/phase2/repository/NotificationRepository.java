package com.blibli.future.phase2.repository;

import com.blibli.future.phase2.entity.Notification;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NotificationRepository extends ReactiveMongoRepository<Notification, String> {
    Flux<Notification> findAllByEmployeeId(String employeeId);

    Flux<Notification> findAllByReturnToAdmin(Boolean returnToAdmin);

    Mono<Boolean> existsByEmployeeIdAndIsRead(String employeeId, Boolean isRead);

    Flux<Notification> findAllByEmployeeIdAndIsRead(String employeeId, Boolean isRead);
}
