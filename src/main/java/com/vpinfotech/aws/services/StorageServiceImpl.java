package com.vpinfotech.aws.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.transfer.internal.MultipleFileUploadImpl;
import com.amazonaws.util.IOUtils;
import com.vpinfotech.aws.dao.StorageRepository;
import com.vpinfotech.aws.entity.ImageStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
public class StorageServiceImpl {

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3Client s3Client;

    @Autowired
    private StorageRepository repository;



    public String uploadFile(MultipartFile file){

        File fileObject= converMultipartFileToFile(file);
        String fileName= System.currentTimeMillis()+"_"+file.getOriginalFilename();
        ImageStorage storage=new ImageStorage(fileName);
        s3Client.putObject(new PutObjectRequest(bucketName,fileName, fileObject));
        storage.setAws_fileName(fileName);
        ImageStorage image= repository.save(storage);
        return "File Uploaded:" +image;
    }

    public byte[] downloadFile(int id){
        byte[] content =null;
        try{
            Optional<ImageStorage> optional= repository.findById(id);
            S3Object s3Object=s3Client.getObject(bucketName,optional.get().getAws_fileName());
            S3ObjectInputStream inputStream= s3Object.getObjectContent();
            content= IOUtils.toByteArray(inputStream);
        }catch(NullPointerException | IOException npe){
            content="Content is null".getBytes();
            System.out.println(npe.getMessage());
        }
        return content;
    }

    private File converMultipartFileToFile(MultipartFile file){
        File convertedFile= new File(file.getOriginalFilename());
        try(FileOutputStream fos= new FileOutputStream(convertedFile)){
            fos.write(file.getBytes());
        }catch(IOException e){
            System.out.println("IOEXception: "+e.getMessage());
        }
        return convertedFile;
    }

    public String deleteFile(String fileName){
        s3Client.deleteObject(bucketName,fileName);
        return "FileName :"+fileName+" is deleted";
    }
}
