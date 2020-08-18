package com.blibli.future.phase2.model.response.admin.notification;

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
public class GetAllNotificationResponse {
    private Set<Notification> notificationList;
}
