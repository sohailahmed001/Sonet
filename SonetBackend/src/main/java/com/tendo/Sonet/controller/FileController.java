package com.tendo.Sonet.controller;

import com.tendo.Sonet.dto.FileUploadResponse;
import com.tendo.Sonet.utils.SONETUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/file")
public class FileController{

    @PostMapping("/uploadSingle")
    private ResponseEntity<FileUploadResponse> createOrUpdateAlbum(@RequestParam("file") MultipartFile multipartFile)
    {
        Assert.notNull(multipartFile, "No file provided");

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();

        String filePath = SONETUtils.saveFile(multipartFile);

        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setSize(size);
        response.setDownloadUri(filePath);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
