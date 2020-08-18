package com.blibli.future.phase2.command.admin.notification;

import com.blibli.future.phase2.model.command.admin.notification.CreateNotificationRequest;
import com.blibli.future.phase2.model.response.admin.notification.CreateNotificationResponse;
import com.blibli.oss.command.Command;

public interface CreateNotificationCommand extends Command<CreateNotificationRequest, CreateNotificationResponse> {

}
