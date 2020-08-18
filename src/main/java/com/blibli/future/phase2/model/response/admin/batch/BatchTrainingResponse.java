package com.blibli.future.phase2.model.response.admin.batch;

import com.blibli.future.phase2.entity.Batch;
import com.blibli.future.phase2.entity.Training;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BatchTrainingResponse {
    private Batch batch;

    private List<Training> trainings;
}
