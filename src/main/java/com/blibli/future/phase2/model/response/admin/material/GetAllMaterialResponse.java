package com.blibli.future.phase2.model.response.admin.material;

import com.blibli.future.phase2.entity.TrainingMaterial;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllMaterialResponse {
    Set<TrainingMaterial> materialList;
}
