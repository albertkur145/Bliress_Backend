package com.blibli.future.phase2.command.admin.employee;

import com.blibli.future.phase2.model.command.admin.employee.CreateEmployeeRequest;
import com.blibli.future.phase2.model.response.admin.employee.CreateEmployeeResponse;
import com.blibli.oss.command.Command;

public interface CreateEmployeeCommand extends Command<CreateEmployeeRequest, CreateEmployeeResponse> {

}
