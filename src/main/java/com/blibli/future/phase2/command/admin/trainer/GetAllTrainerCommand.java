package com.blibli.future.phase2.command.admin.trainer;

import com.blibli.future.phase2.model.command.BlankRequest;
import com.blibli.future.phase2.model.response.admin.trainer.CreateTrainerResponse;
import com.blibli.future.phase2.model.response.admin.trainer.GetAllTrainerResponse;
import com.blibli.oss.command.Command;

public interface GetAllTrainerCommand extends Command<BlankRequest, GetAllTrainerResponse> {
}
