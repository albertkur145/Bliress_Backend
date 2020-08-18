package com.blibli.future.phase2.command.admin.notification;

import com.blibli.future.phase2.model.command.BlankRequest;
import com.blibli.future.phase2.model.response.admin.notification.GetAllNotificationResponse;
import com.blibli.oss.command.Command;

public interface GetAllNotificationCommand extends Command<BlankRequest, GetAllNotificationResponse> {

}
