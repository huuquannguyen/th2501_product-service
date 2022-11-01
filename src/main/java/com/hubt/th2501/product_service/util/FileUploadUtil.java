package com.hubt.th2501.product_service.util;

import com.hubt.th2501.product_service.constant.ErrorCode;
import com.hubt.th2501.product_service.exception.ApiException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {
    public static String uploadImage(Long productId, MultipartFile file) throws ApiException, IOException {
        if (file == null) {
            throw new ApiException(ErrorCode.UNKNOWN_ERROR.getCode(), "The file is null");
        }
        Path uploadPath = Paths.get("files-upload/product-image");
        String fileName = file.getOriginalFilename();
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Path filePath = uploadPath.resolve("product-" + productId + fileExtension);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ApiException(ErrorCode.UPLOAD_IMAGE_ERROR);
        }
        return filePath.toString();
    }
}
