package com.blibli.future.phase2.model.command.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
    @NotBlank(message = "Mail is not filled")
    private String usermail;

    @NotBlank(message = "Password is not filled")
    private String password;
}
