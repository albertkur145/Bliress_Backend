package com.blibli.future.phase2.command.employee.training;

import com.blibli.future.phase2.model.command.user.training.SubmitAttendanceTrainingRequest;
import com.blibli.future.phase2.model.response.user.training.SubmitAttendanceTrainingResponse;
import com.blibli.oss.command.Command;

public interface SubmitAttendanceTrainingCommand extends Command<SubmitAttendanceTrainingRequest, SubmitAttendanceTrainingResponse> {

}
