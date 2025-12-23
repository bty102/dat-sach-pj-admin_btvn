package com.bty.datsachpj.datsachpjadmin_btvn.infrastructure;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileUtil {

    public String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }

    public Path saveImageFile(MultipartFile file, String dir) throws Exception {
        String fileName = file.getOriginalFilename(); // VD: file.txt
        String fileExtension = getFileExtension(fileName).toLowerCase();
        if (!fileExtension.equals("jpg")
        && !fileExtension.equals("png")) {
            throw new Exception("Định dạng file không hợp lệ");
        }
        String fullFilePath = dir + UUID.randomUUID().toString() + "_" + fileName;
        Path path = Paths.get(fullFilePath);
        Path returnedPath = Files.write(path, file.getBytes());
        return returnedPath;
    }
    // VD: filePath=D:/ht/a.txt
    public boolean deleteFile(String filePath) throws Exception {
        Path path = Paths.get(filePath);
        return Files.deleteIfExists(path);
    }
}
