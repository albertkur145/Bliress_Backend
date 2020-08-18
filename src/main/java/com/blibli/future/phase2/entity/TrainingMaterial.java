package com.blibli.future.phase2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class TrainingMaterial {
    @Id
    private String materialId;

    private String batchId;

    private Integer stage;

    private String materialName;

    private String file;

    private Boolean testExist;

    private String testAvailable;

    private String testClosed;

    private Integer testTimeLimit;
}
