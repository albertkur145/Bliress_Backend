package com.blibli.future.phase2.command.auth;

import com.blibli.future.phase2.model.command.auth.CreateUserRequest;
import com.blibli.future.phase2.model.response.auth.CreateUserResponse;
import com.blibli.oss.command.Command;

public interface CreateUserCommand extends Command<CreateUserRequest, CreateUserResponse> {

}
