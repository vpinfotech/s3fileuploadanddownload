package com.vpinfotech.aws.controller;

import com.vpinfotech.aws.entity.ImageStorage;
import com.vpinfotech.aws.services.StorageServiceImpl;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class StorageController {

    @Autowired
    private StorageServiceImpl storageService;

    @PostMapping(value="/attach_file")
    public ResponseEntity<String> uploadFile(@RequestParam(value="file") MultipartFile file){
        return new ResponseEntity<>(storageService.uploadFile(file), HttpStatus.OK);
    }

    @GetMapping(value = "/download/{id}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable int id){
        byte[] data= storageService.downloadFile(id);
        String fileName= null;
        ByteArrayResource resource= new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("ContentType", "image/png")
                .header("content-disposition", "attachment; fileName=\""+System.currentTimeMillis()+"-"+id+".png"+"\"")
                .body(resource);
    }

    @DeleteMapping(value="/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName){
        return new ResponseEntity<>(storageService.deleteFile(fileName),HttpStatus.OK);
    }
}
