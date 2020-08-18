package com.blibli.future.phase2.model.command.admin.trainer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTrainerRequest {
    private String id;

    private String name;

    private String email;

    private String division;
}
