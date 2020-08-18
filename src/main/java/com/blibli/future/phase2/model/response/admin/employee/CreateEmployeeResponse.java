package com.blibli.future.phase2.model.response.admin.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateEmployeeResponse {
    private HttpStatus status;

    private String message;
}
