package com.blibli.future.phase2.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileUploadUtil {

//    private String temp = "/blibli-experience/src/main/resources/";
//    private String projectDir = "D:\\Projects\\BlibliFuture\\Phase 2\\backend-service" + temp;
//    private String pathServer = "http://192.168.43.138:8080/";

    @Value("${file.upload-dir}")
    private String uploadDir;

//    public List<String> uploadAllPhoto(List<MultipartFile> photos,
//                                       UUID productId,
//                                       UploadEnum uploadEnum) throws IOException {
//        List<String> imagePaths = new ArrayList<>();
//        for (int i = 0; i < photos.size(); i++) {
//            imagePaths.add(uploadPhoto(photos.get(i), productId, uploadEnum, i));
//        }
//        return imagePaths;
//    }

    public String uploadTrainingFile(MultipartFile file,
                              String batchId,
                              String stage) throws IOException {
        String fileLink = uploadDir + "/training/" + batchId + "/" + stage + "/" + file.getOriginalFilename();
        File tempFile = new File(fileLink);
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        } else {
            tempFile.delete();
        }
        file.transferTo(tempFile);

        String url = "/files/training/" + batchId + "/" + stage + "/" + file.getOriginalFilename();
        return url;
    }

    public Boolean validatePhoto(MultipartFile multipartFile) throws IOException {
        return multipartFile.getContentType().contains("image/");
    }

}
