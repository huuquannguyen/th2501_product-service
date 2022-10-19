package com.hubt.th2501.product_service.util;

import com.hubt.th2501.product_service.constants.ErrorCode;
import com.hubt.th2501.product_service.exception.ApiException;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {
    public static String uploadImage(Long productId, MultipartFile file) throws ApiException, IOException {
        Path uploadPath = Paths.get("files-upload/product-image");
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        Path filePath = uploadPath.resolve("product-" + productId);
        try(InputStream inputStream = file.getInputStream()){
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            throw new ApiException(ErrorCode.UPLOAD_IMAGE_ERROR);
        }
        return filePath.toString();
    }
}
