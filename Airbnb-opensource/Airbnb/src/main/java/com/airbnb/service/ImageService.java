package com.airbnb.service;

import com.airbnb.payload.ImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    public ImageDto addImage(long propertyId, MultipartFile file);
    public List<ImageDto> fetchPropertyImages(long propertyId);
    public Boolean deleteImaqe(long imgeId);
}
