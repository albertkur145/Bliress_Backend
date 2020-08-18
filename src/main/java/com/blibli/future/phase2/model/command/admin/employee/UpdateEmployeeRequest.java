package com.blibli.future.phase2.model.command.admin.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateEmployeeRequest {
    private String id;

    private String name;

    private String email;

    private String phoneNumber;

    private String division;

    @Pattern(regexp = "^[0-3]\\d-[0-1]\\d-\\d\\d\\d\\d$", message = "Invalid Date")
    private String birthdate;

    private String gender;

    private String batchId;
}
