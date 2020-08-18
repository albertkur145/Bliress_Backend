package com.blibli.future.phase2.model.command.admin.test;

import com.blibli.future.phase2.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTestRequest {
    private String batchId;

    private String training;

    private String materialId;

    private String available;

    private String closed;

    private Integer timeLimit;

    private Set<Question> questions;
}
