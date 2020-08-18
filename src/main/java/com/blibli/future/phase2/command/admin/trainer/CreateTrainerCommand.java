package com.blibli.future.phase2.command.admin.trainer;

import com.blibli.future.phase2.model.command.admin.trainer.CreateTrainerRequest;
import com.blibli.future.phase2.model.response.admin.trainer.CreateTrainerResponse;
import com.blibli.oss.command.Command;

public interface CreateTrainerCommand extends Command<CreateTrainerRequest, CreateTrainerResponse> {

}
