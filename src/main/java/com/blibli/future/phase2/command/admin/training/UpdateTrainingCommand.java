package com.blibli.future.phase2.command.admin.training;

import com.blibli.future.phase2.model.command.admin.training.UpdateTrainingRequest;
import com.blibli.future.phase2.model.response.admin.training.UpdateTrainingResponse;
import com.blibli.oss.command.Command;

public interface UpdateTrainingCommand extends Command<UpdateTrainingRequest, UpdateTrainingResponse> {
}
