package com.blibli.future.phase2.command.auth;

import com.blibli.future.phase2.model.command.auth.ChangePasswordRequest;
import com.blibli.future.phase2.model.response.auth.ChangePasswordResponse;
import com.blibli.oss.command.Command;

public interface ChangePasswordCommand extends Command<ChangePasswordRequest, ChangePasswordResponse> {

}
