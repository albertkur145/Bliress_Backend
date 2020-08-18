package com.blibli.future.phase2.command.admin.training;

import com.blibli.future.phase2.entity.Training;
import com.blibli.future.phase2.model.command.admin.training.GetByIdTrainingRequest;
import com.blibli.oss.command.Command;

public interface GetByIdTrainingCommand extends Command<GetByIdTrainingRequest, Training> {
}
