package com.blibli.future.phase2.model.command.admin.material;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateMaterialRequest {
    private String batchId;

    private String training;

    private String materialName;
}
