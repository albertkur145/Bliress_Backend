package com.blibli.future.phase2.command.admin.notification.impl;

import com.blibli.future.phase2.command.admin.notification.GetAllNotificationCommand;
import com.blibli.future.phase2.model.command.BlankRequest;
import com.blibli.future.phase2.model.response.admin.notification.GetAllNotificationResponse;
import com.blibli.future.phase2.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashSet;

@Service
public class GetAllNotificationCommandImpl implements GetAllNotificationCommand {
    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Mono<GetAllNotificationResponse> execute(BlankRequest request) {
        return Mono.fromCallable(() -> getNotificationAndResponse());
    }

    public GetAllNotificationResponse getNotificationAndResponse(){
        return GetAllNotificationResponse.builder()
                .notificationList(
                        new HashSet<>(Collections.unmodifiableList(
                                notificationRepository.findAllByReturnToAdmin(Boolean.TRUE).collectList().block()
                        ))
                )
                .build();
    }
}
