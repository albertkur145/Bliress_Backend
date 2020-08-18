package com.blibli.future.phase2.model.response.admin.training;

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
public class GetAllByTrainerIdResponse {
    private Set<Training> trainingList;
}
