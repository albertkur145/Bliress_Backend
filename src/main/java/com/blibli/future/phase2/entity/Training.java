package com.blibli.future.phase2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Training {
    @Id
    private String trainingId;

    private String batchId;

    private Integer stage;

    private String date;

    private String startedAt;

    private String endedAt;

    private String location;

    private String trainerId;

    private String trainerName;
}
