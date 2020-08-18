package com.blibli.future.phase2.command.employee.notification.impl;

import com.blibli.future.phase2.command.employee.notification.ReadNotificationEmplCommand;
import com.blibli.future.phase2.component.AuthenticatedUserProvider;
import com.blibli.future.phase2.entity.Notification;
import com.blibli.future.phase2.model.response.admin.notification.ReadNotificationEmplResponse;
import com.blibli.future.phase2.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

@Service
public class ReadNotificationEmplCommandImpl implements ReadNotificationEmplCommand {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private AuthenticatedUserProvider authenticatedUserProvider;

    @Override
    public Mono<ReadNotificationEmplResponse> execute(String request) {
        return Mono.fromCallable(() -> getAllUnreadNotification(
                (String) authenticatedUserProvider.getValueFromUserData(request, "userId"))
        )
                .map(notifications -> setNotificationAsRead(notifications))
                .flatMap(notifications -> notificationRepository.saveAll(notifications).then(Mono.just(Boolean.TRUE)))
                .thenReturn(createResponse(HttpStatus.ACCEPTED, "Notification has been read"))
                .onErrorReturn(createResponse(HttpStatus.BAD_REQUEST, "Notification cannot be read"));

    }

    private Set<Notification> getAllUnreadNotification(String employeeId){
        return new HashSet<>(
                notificationRepository.findAllByEmployeeIdAndIsRead(employeeId, Boolean.FALSE).collectList().block()
        );
    }

    private Set<Notification> setNotificationAsRead(Set<Notification> notifications){
        Set<Notification> savedList = new HashSet<>();
        for(Notification notification : notifications){
            Notification saved = notification;
            saved.setIsRead(Boolean.TRUE);
            savedList.add(saved);
        }
        return savedList;
    }

    private ReadNotificationEmplResponse createResponse(HttpStatus status, String message){
        return ReadNotificationEmplResponse.builder()
                .status(status)
                .message(message)
                .build();
    }
}
