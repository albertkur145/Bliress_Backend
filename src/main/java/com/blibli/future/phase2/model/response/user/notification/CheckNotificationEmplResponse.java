package com.blibli.future.phase2.model.response.user.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckNotificationEmplResponse {
    private Boolean hasNotif;
}
