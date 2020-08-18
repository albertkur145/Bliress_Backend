package com.blibli.future.phase2.command.admin.training;

import com.blibli.future.phase2.model.command.admin.training.CreateTrainingRequest;
import com.blibli.future.phase2.model.response.admin.training.CreateTrainingResponse;
import com.blibli.oss.command.Command;

public interface CreateTrainingCommand extends Command<CreateTrainingRequest, CreateTrainingResponse> {
}
