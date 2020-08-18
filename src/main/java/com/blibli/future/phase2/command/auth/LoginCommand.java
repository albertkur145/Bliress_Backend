package com.blibli.future.phase2.command.auth;

import com.blibli.future.phase2.model.command.auth.LoginRequest;
import com.blibli.future.phase2.model.response.auth.LoginResponse;
import com.blibli.oss.command.Command;

public interface LoginCommand extends Command<LoginRequest, LoginResponse> {

}
