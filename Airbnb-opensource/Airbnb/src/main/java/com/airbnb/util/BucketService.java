package com.airbnb.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URL;

@Service
public class BucketService {

    @Autowired
    private AmazonS3 amazonS3;

    private String bucketName = "shankarbhola1";

    public String uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        try {
            File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            file.transferTo(convFile);
            try {
                amazonS3.putObject(bucketName, convFile.getName(), convFile);
                return amazonS3.getUrl(bucketName, file.getOriginalFilename()).toString();
            } catch (AmazonS3Exception s3Exception) {
                return "Unable to upload file :" + s3Exception.getMessage();
            }



        } catch (Exception e) {
            throw new IllegalStateException("Failed to upload file", e);
        }

    }

    public void deleteFileByUrl(String bucketName,String fileUrl) {
        try {
            URL url = new URL(fileUrl);
            String path = url.getPath();
            System.out.println(path);
            // Assuming the key is the part of the path after the bucket name
            String key = path.substring(1); // Remove the leading '/'
            System.out.println(key);
            if (key != null) {
                amazonS3.deleteObject(bucketName, key);
            }
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }

    }

//    public String deleteBucket(String bucketName) {
//        amazonS3.deleteBucket(bucketName);
//        return "File is deleted";
//    }

}

