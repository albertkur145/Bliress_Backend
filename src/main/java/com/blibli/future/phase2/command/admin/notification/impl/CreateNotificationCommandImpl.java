package com.blibli.future.phase2.command.admin.notification.impl;

import com.blibli.future.phase2.command.admin.notification.CreateNotificationCommand;
import com.blibli.future.phase2.entity.Notification;
import com.blibli.future.phase2.entity.User;
import com.blibli.future.phase2.model.command.admin.notification.CreateNotificationRequest;
import com.blibli.future.phase2.model.response.admin.notification.CreateNotificationResponse;
import com.blibli.future.phase2.repository.BatchRepository;
import com.blibli.future.phase2.repository.NotificationRepository;
import com.blibli.future.phase2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class CreateNotificationCommandImpl implements CreateNotificationCommand {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BatchRepository batchRepository;

    @Override
    public Mono<CreateNotificationResponse> execute(CreateNotificationRequest request) {
        return Mono.fromCallable(() -> getAllUsersByBatch(request.getBatchId()))
                .switchIfEmpty(Mono.just(new HashSet<>()))
                .flatMap(users -> (users.isEmpty()) ? Mono.just(null) : notificationRepository.saveAll(createNotificationList(request, users))
                        .then(Mono.just(Boolean.TRUE)))
                .thenReturn(createResponse(HttpStatus.ACCEPTED, "Notification has been created"))
                .onErrorReturn(createResponse(HttpStatus.BAD_REQUEST, "Notification cannot be created"));
    }

    private Mono<Boolean> checkIfBatchExist(String batchId){
        return batchRepository.existsById(batchId);
    }

    private Set<Notification> createNotificationList(CreateNotificationRequest request, Set<User> users){
        Set<Notification> savedList = new HashSet<>(
                Collections.singleton(createNotification(request, null, Boolean.TRUE)));

        for (User user : users) {
            savedList.add(
                createNotification(request, user, Boolean.FALSE)
            );
        }
        return savedList;
    }

    private Notification createNotification(CreateNotificationRequest request, User user, Boolean returnToAdmin){
        return Notification.builder()
                .id(UUID.randomUUID().toString())
                .sender("ADMIN")
                .title(request.getTitle())
                .message(request.getMessage())
                .batch(request.getBatchId())
                .employeeId((user == null) ? null : user.getUserId())
                .isRead(Boolean.FALSE)
                .returnToAdmin(returnToAdmin)
                .createdAt(LocalDateTime.now())
                .build();
    }

    private Set<User> getAllUsersByBatch(String batchId){
        return new HashSet<>(userRepository.findAllByBatchOrderByUsernameAsc(batchId).collectList().block());
    }

    private CreateNotificationResponse createResponse(HttpStatus status, String message){
        return CreateNotificationResponse.builder()
                .status(status)
                .message(message)
                .build();
    }
}
