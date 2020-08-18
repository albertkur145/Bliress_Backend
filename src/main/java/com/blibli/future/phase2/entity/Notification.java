package com.blibli.future.phase2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Notification {
    @Id
    private String id;

    private String sender;

    private String employeeId;

    private String batch;

    private String title;

    private String message;

    private Boolean isRead;

    private Boolean returnToAdmin;

    private LocalDateTime createdAt;
}
