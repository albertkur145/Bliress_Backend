package com.blibli.future.phase2.model.response.admin.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateNotificationResponse {
    private HttpStatus status;

    private String message;
}
