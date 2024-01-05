package com.tendo.Sonet.utils;

import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.nio.file.*;
import java.util.*;

public class SONETUtils
{
    private static  final   Logger  logger                  =   LoggerFactory.getLogger(SONETUtils.class);
    private static  final   String  STATIC_RESOURCE_PATH    =   "/src/main/resources/static";
    private static  final   String  IMAGES_DIR              =   "/images";
    private static  final   String  AUDIO_DIR               =   "/audios";
    private static  final   String  UPLOAD_DIRECTORY        =   System.getProperty("user.dir") + STATIC_RESOURCE_PATH; ;

    public static String processImage(MultipartFile file, boolean chooseSongPath)
    {
        try
        {
            if (file.isEmpty())
            {
                throw new IllegalArgumentException("File is empty");
            }

            // random ID to avoid naming conflict
            String  randomID            =   UUID.randomUUID().toString();
            String  originalFileName    =   StringUtils.cleanPath(file.getOriginalFilename());
            String  fileExtension       =   StringUtils.getFilenameExtension(originalFileName);
            String  fileName            =   randomID + "." + fileExtension;
            String  resourceDirectory   =   chooseSongPath ? AUDIO_DIR : IMAGES_DIR;
            String  dirPath             =   UPLOAD_DIRECTORY + "/" + resourceDirectory;
            Path    uploadPath          =   Paths.get(dirPath);

            if (!Files.exists(uploadPath))
            {
                Files.createDirectories(uploadPath);
            }

            Path    filePath    =   uploadPath.resolve(fileName).normalize();


            // saving file in directory
            Files.copy(file.getInputStream(), filePath);

            return resourceDirectory + "/" + fileName;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new IllegalStateException("Couldn't save image");
        }
    }

    public static void deleteIfFileExist(String filePath)
    {
        Path fileCurrentPath = Paths.get(filePath);

        if (Files.exists(fileCurrentPath))
        {
            try
            {
                Files.delete(fileCurrentPath);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                throw new IllegalStateException("Couldn't save image");
            }
        }
    }

    public static String saveFile(MultipartFile multipartFile) {
        Assert.hasLength(multipartFile.getContentType(), "File does not have a content type");

        if(multipartFile.getContentType().startsWith("image/")) {
            return processImage(multipartFile, false);
        }
        throw new RuntimeException("Server currently only handles image uploads"); // TODO
    }

    public static byte[] retrieveFile(String resourcePath) {
        logger.info("Resource Requested: {}", resourcePath);

        String  filePath =   UPLOAD_DIRECTORY + "/" + resourcePath;

        logger.info("File Path: {}", filePath);

        File file = new File(filePath);

        if (file.exists()) {
            try {
                return Files.readAllBytes(file.toPath());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
