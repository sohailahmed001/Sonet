package com.tendo.Sonet.utils;

import java.io.*;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.nio.file.*;
import java.util.*;

public class SONETUtils
{
    private static  final    String STATIC_RESOURCE_PATH    =   "/SonetBackend/src/main/resources/static";
    private static  final    String IMAGES_DIR              =   "/images";
    private static  final    String AUDIO_DIR               =   "/audios";
    private static  final    String UPLOAD_DIRECTORY        =   System.getProperty("user.dir") + STATIC_RESOURCE_PATH; ;

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
            String  dirPath             =   chooseSongPath ? getAudioDir() : getImagesDir();
            Path    uploadPath          =   Paths.get(dirPath);

            if (!Files.exists(uploadPath))
            {
                Files.createDirectories(uploadPath);
            }

            Path    filePath    =   uploadPath.resolve(fileName).normalize();


            // saving file in directory
            Files.copy(file.getInputStream(), filePath);

            return uploadPath.toAbsolutePath() + "/" + fileName;
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

    private static String getImagesDir()
    {
        return UPLOAD_DIRECTORY + "/" + IMAGES_DIR;
    }

    private static String getAudioDir()
    {
        return UPLOAD_DIRECTORY + "/" + AUDIO_DIR;
    }
}
