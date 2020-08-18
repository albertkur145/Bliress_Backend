package com.blibli.future.phase2.command.admin.batch;

import com.blibli.future.phase2.model.command.BlankRequest;
import com.blibli.future.phase2.model.response.admin.batch.GetAllBatchResponse;
import com.blibli.oss.command.Command;

public interface GetAllBatchCommand extends Command<BlankRequest, GetAllBatchResponse> {

}
