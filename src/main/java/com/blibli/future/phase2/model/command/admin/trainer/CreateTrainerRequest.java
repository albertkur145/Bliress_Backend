package com.blibli.future.phase2.model.command.admin.trainer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTrainerRequest {
    private String name;

    private String email;

    private String password;

    private String division;
}
