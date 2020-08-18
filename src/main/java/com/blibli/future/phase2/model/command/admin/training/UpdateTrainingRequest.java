package com.blibli.future.phase2.model.command.admin.training;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTrainingRequest {
    private String batchId;

    private String training;

    @Pattern(regexp = "^[0-3]\\d-[0-1]\\d-\\d\\d\\d\\d$", message = "Invalid Date")
    private String date;

    private String location;

    private String timeStart;

    private String timeFinish;

    private String trainerId;

    private String trainerName;
}
