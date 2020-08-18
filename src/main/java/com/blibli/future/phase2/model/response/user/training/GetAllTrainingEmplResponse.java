package com.blibli.future.phase2.model.response.user.training;

import com.blibli.future.phase2.entity.Training;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllTrainingEmplResponse {
    private Set<Training> trainingList;
}
