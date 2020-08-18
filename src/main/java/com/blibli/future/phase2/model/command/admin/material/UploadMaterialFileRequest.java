package com.blibli.future.phase2.model.command.admin.material;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.codec.multipart.FilePart;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadMaterialFileRequest {
//    private String materialId;

    private FilePart file;
}
