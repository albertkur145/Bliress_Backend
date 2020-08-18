package com.blibli.future.phase2.model.response.user.notification;

import com.blibli.future.phase2.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllNotificationEmplResponse {
    private Set<Notification> notificationList;
}
