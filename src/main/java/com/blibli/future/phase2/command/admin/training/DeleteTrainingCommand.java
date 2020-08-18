package com.blibli.future.phase2.command.admin.training;

import com.blibli.future.phase2.model.command.admin.training.DeleteTrainingRequest;
import com.blibli.future.phase2.model.response.admin.training.DeleteTrainingResponse;
import com.blibli.oss.command.Command;

public interface DeleteTrainingCommand extends Command<DeleteTrainingRequest, DeleteTrainingResponse> {
}
