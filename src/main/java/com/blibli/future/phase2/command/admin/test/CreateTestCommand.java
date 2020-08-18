package com.blibli.future.phase2.command.admin.test;

import com.blibli.future.phase2.model.command.admin.test.CreateTestRequest;
import com.blibli.future.phase2.model.response.admin.test.CreateTestResponse;
import com.blibli.oss.command.Command;

public interface CreateTestCommand extends Command<CreateTestRequest, CreateTestResponse> {

}
