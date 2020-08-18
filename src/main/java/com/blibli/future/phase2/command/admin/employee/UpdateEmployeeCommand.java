package com.blibli.future.phase2.command.admin.employee;

import com.blibli.future.phase2.model.command.admin.employee.UpdateEmployeeRequest;
import com.blibli.future.phase2.model.response.admin.employee.UpdateEmployeeResponse;
import com.blibli.oss.command.Command;

public interface UpdateEmployeeCommand extends Command<UpdateEmployeeRequest, UpdateEmployeeResponse> {

}
