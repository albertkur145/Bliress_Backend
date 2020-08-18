package com.blibli.future.phase2.model.response.user.training;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmitAttendanceTrainingResponse {
    private HttpStatus status;

    private String message;
}
