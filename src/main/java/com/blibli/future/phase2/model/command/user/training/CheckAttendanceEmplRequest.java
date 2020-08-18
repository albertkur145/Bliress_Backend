package com.blibli.future.phase2.model.command.user.training;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckAttendanceEmplRequest {
    private String training;

    private String token;
}
