package com.blibli.future.phase2.command.admin.file;

import com.blibli.future.phase2.model.command.admin.material.UploadMaterialFileRequest;
import com.blibli.oss.command.Command;
import org.springframework.http.codec.multipart.FilePart;

public interface MaterialFileUploadCommand extends Command<FilePart, String> {

}
