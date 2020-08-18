package com.blibli.future.phase2.model.command.user.training;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmitAttendanceTrainingRequest {
    private String employeeId;

    private String training;

    private String token;
}
