package com.airbnb.service;

import com.airbnb.exception.ResourceNotFound;
import com.airbnb.entity.Images;
import com.airbnb.entity.Property;
import com.airbnb.payload.ImageDto;
import com.airbnb.repository.ImagesRepository;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.util.BucketService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {

    private ModelMapper modelMapper;
    private PropertyRepository propertyRepository;
    private BucketService bucketService;
    private ImagesRepository imagesRepository;

    public ImageServiceImpl(ModelMapper modelMapper, PropertyRepository propertyRepository, BucketService bucketService, ImagesRepository imagesRepository) {
        this.modelMapper = modelMapper;
        this.propertyRepository = propertyRepository;
        this.bucketService = bucketService;
        this.imagesRepository = imagesRepository;
    }

    @Override
    public ImageDto addImage(long propertyId, MultipartFile file) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(
                () -> new ResourceNotFound("Property Not Found with id " + propertyId)
        );
        String url = bucketService.uploadFile(file);
        Images images = new Images();
        images.setProperty(property);
        images.setImageUrl(url);
        Images savedImage = imagesRepository.save(images);

        ImageDto dto = modelMapper.map(savedImage, ImageDto.class);

        return dto;
    }

    @Override
    public List<ImageDto> fetchPropertyImages(long propertyId) {
        List<Images> images = imagesRepository.findByPropertyId(propertyId);
        List<ImageDto> dto = images.stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .collect(Collectors.toList());
        return dto;
    }

    @Override
    public Boolean deleteImaqe(long imgeId) {
        Optional<Images> byId = imagesRepository.findById(imgeId);
        if (byId.isPresent()){
            imagesRepository.deleteById(imgeId);
            return true;
        }
        return false;
    }
}
