package com.blibli.future.phase2.command.employee.notification.impl;

import com.blibli.future.phase2.command.employee.notification.CheckNotificationEmplCommand;
import com.blibli.future.phase2.component.AuthenticatedUserProvider;
import com.blibli.future.phase2.model.response.user.notification.CheckNotificationEmplResponse;
import com.blibli.future.phase2.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CheckNotificationEmplCommandImpl implements CheckNotificationEmplCommand {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private AuthenticatedUserProvider authenticatedUserProvider;

    @Override
    public Mono<CheckNotificationEmplResponse> execute(String request) {
        return notificationRepository.existsByEmployeeIdAndIsRead(
                (String) authenticatedUserProvider.getValueFromUserData(request, "userId"),
                Boolean.FALSE
        ).map(result -> createResponse(result));
    }

    private CheckNotificationEmplResponse createResponse(Boolean status){
        return CheckNotificationEmplResponse.builder()
                .hasNotif(status)
                .build();
    }
}
