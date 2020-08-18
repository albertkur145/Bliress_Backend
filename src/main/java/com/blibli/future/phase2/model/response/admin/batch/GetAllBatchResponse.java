package com.blibli.future.phase2.model.response.admin.batch;

import com.blibli.future.phase2.entity.Batch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllBatchResponse {
    private Set<Batch> batchList;
}
