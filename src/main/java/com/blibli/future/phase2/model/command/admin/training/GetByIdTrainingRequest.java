package com.blibli.future.phase2.model.command.admin.training;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetByIdTrainingRequest {
    private String batchId;

    private String training;
}
