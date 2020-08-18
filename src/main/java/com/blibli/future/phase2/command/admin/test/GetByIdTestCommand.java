package com.blibli.future.phase2.command.admin.test;

import com.blibli.future.phase2.entity.Test;
import com.blibli.future.phase2.model.command.admin.test.GetByIdTestRequest;
import com.blibli.oss.command.Command;

public interface GetByIdTestCommand extends Command<GetByIdTestRequest, Test> {

}
