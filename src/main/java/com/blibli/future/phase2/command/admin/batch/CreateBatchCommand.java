package com.blibli.future.phase2.command.admin.batch;

import com.blibli.future.phase2.model.command.admin.batch.CreateBatchRequest;
import com.blibli.future.phase2.model.response.admin.batch.CreateBatchResponse;
import com.blibli.oss.command.Command;

public interface CreateBatchCommand extends Command<CreateBatchRequest, CreateBatchResponse> {

}
