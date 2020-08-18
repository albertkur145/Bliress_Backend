package com.blibli.future.phase2.model.command.admin.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateNotificationRequest {
    private String batchId;

    private String title;

    private String message;
}
