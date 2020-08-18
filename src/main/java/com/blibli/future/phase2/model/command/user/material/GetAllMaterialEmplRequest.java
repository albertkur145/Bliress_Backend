package com.blibli.future.phase2.model.command.user.material;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllMaterialEmplRequest {
    private String employeeId;

    private String training;
}
