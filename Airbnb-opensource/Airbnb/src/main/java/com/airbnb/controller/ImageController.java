package com.airbnb.controller;

import com.airbnb.payload.ImageDto;
import com.airbnb.service.ImageService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/images/")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/addImage")
    public ResponseEntity<?> addImage(@RequestParam long propertyId, MultipartFile file){
        ImageDto dto = imageService.addImage(propertyId, file);
        if (dto!=null){
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Failed to upload Image", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/propertyImages")
    public ResponseEntity<?> fetchPropertyImages(@RequestParam long propertyId) {
        List<ImageDto> imageDtos = imageService.fetchPropertyImages(propertyId);
        if (!imageDtos.isEmpty()){
            return new ResponseEntity<>(imageDtos,HttpStatus.OK);
        }
        return new ResponseEntity<>("No images found",HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteimage")
    public ResponseEntity<?> deleteimage(@RequestParam long imageId){
        Boolean status = imageService.deleteImaqe(imageId);
        if (status){
            return new ResponseEntity<>("Image Deleted Successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("No image found",HttpStatus.NOT_FOUND);
    }
}
