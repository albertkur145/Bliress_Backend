package com.blibli.future.phase2.command.admin.employee;

import com.blibli.future.phase2.model.command.BlankRequest;
import com.blibli.future.phase2.model.response.admin.employee.GetAllEmployeeResponse;
import com.blibli.oss.command.Command;

public interface GetAllEmployeeCommand extends Command<BlankRequest, GetAllEmployeeResponse> {

}
