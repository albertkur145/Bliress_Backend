package com.blibli.future.phase2.command.employee.notification.impl;

import com.blibli.future.phase2.command.employee.notification.GetAllNotificationEmplCommand;
import com.blibli.future.phase2.component.AuthenticatedUserProvider;
import com.blibli.future.phase2.entity.Notification;
import com.blibli.future.phase2.model.response.user.notification.GetAllNotificationEmplResponse;
import com.blibli.future.phase2.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

@Service
public class GetAllNotificationEmplCommandImpl implements GetAllNotificationEmplCommand {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private AuthenticatedUserProvider authenticatedUserProvider;

    @Override
    public Mono<GetAllNotificationEmplResponse> execute(String request) {
        return Mono.fromCallable(() -> getAllNotifications(
                (String) authenticatedUserProvider.getValueFromUserData(request, "userId")
        )).map(notifications -> createResponse(notifications));
    }

    private Set<Notification> getAllNotifications(String employeeId){
        return new HashSet<>(
                notificationRepository.findAllByEmployeeIdAndIsRead(employeeId, Boolean.FALSE).collectList().block()
        );
    }

    private GetAllNotificationEmplResponse createResponse(Set<Notification> notifications){
        return GetAllNotificationEmplResponse.builder()
                .notificationList(notifications)
                .build();
    }
}
