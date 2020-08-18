package com.blibli.future.phase2.model.command.admin.batch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBatchRequest {
    @NotBlank
    private String batch;

    @NotBlank
    private String year;
}
