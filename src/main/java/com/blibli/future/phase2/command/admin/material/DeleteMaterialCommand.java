package com.blibli.future.phase2.command.admin.material;

import com.blibli.future.phase2.model.command.admin.material.DeleteMaterialRequest;
import com.blibli.future.phase2.model.response.admin.material.DeleteMaterialResponse;
import com.blibli.oss.command.Command;

public interface DeleteMaterialCommand extends Command<DeleteMaterialRequest, DeleteMaterialResponse> {

}
