package com.blibli.future.phase2.command.admin.material;

import com.blibli.future.phase2.model.command.admin.material.CreateMaterialRequest;
import com.blibli.future.phase2.model.response.admin.material.CreateMaterialResponse;
import com.blibli.oss.command.Command;

public interface CreateMaterialCommand extends Command<CreateMaterialRequest, CreateMaterialResponse> {

}
