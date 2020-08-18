package com.blibli.future.phase2.command.admin.material;

import com.blibli.future.phase2.model.command.admin.material.GetAllMaterialRequest;
import com.blibli.future.phase2.model.response.admin.material.GetAllMaterialResponse;
import com.blibli.oss.command.Command;

public interface GetAllMaterialCommand extends Command<GetAllMaterialRequest, GetAllMaterialResponse> {

}
