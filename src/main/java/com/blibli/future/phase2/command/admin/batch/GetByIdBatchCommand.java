package com.blibli.future.phase2.command.admin.batch;

import com.blibli.future.phase2.entity.Batch;
import com.blibli.future.phase2.model.response.admin.batch.GetByIdBatchResponse;
import com.blibli.oss.command.Command;

public interface GetByIdBatchCommand extends Command<String, Batch> {
}
