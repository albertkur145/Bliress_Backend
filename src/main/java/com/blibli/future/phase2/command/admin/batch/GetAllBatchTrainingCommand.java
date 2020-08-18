package com.blibli.future.phase2.command.admin.batch;

import com.blibli.future.phase2.model.command.BlankRequest;
import com.blibli.future.phase2.model.response.admin.batch.BatchTrainingResponse;
import com.blibli.oss.command.Command;

import java.util.List;
import java.util.Set;

public interface GetAllBatchTrainingCommand extends Command<BlankRequest, List<BatchTrainingResponse>> {

}
