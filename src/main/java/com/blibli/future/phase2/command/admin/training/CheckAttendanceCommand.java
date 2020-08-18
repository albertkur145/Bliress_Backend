package com.blibli.future.phase2.command.admin.training;

import com.blibli.future.phase2.model.command.admin.training.CheckAttendanceRequest;
import com.blibli.future.phase2.model.response.admin.training.CheckAttendanceResponse;
import com.blibli.oss.command.Command;

public interface CheckAttendanceCommand extends Command<CheckAttendanceRequest, CheckAttendanceResponse> {

}
