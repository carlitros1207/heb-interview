package net.pspman.heb.service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.Feature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.pspman.heb.repository.ImageRepository;
import net.pspman.heb.model.DetectedObjectEntity;
import net.pspman.heb.model.ImageEntity;
import net.pspman.heb.model.ImageRequest;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final CloudVisionTemplate cloudVisionTemplate;

    private final ResourceLoader loader;

    private final ImageRepository imageRepository;

    public List<ImageEntity> getAllImages(){
        return imageRepository.findAll();
    }

    public List<ImageEntity> getImagesByObjects(List<String> objects) {
        return imageRepository.findAllByDetected(objects);
    }

    public ImageEntity getImageById(BigInteger id){
        return imageRepository.getById(id);
    }

    public ImageEntity processImage(ImageRequest request) {
        Resource resource = loader.getResource(request.getUrl());

        ImageEntity imageEntity = new ImageEntity(request);

        if(request.isEnableDetection()){
            AnnotateImageResponse response = cloudVisionTemplate.analyzeImage(resource, Feature.Type.LABEL_DETECTION);
            response.getLabelAnnotationsList().forEach(label ->{
                DetectedObjectEntity objectEntity = new DetectedObjectEntity();
                objectEntity.setObject(label.getDescription());
                imageEntity.getDetectedObjects().add(objectEntity);
                objectEntity.setImageEntity(imageEntity);
            });
        }


        return imageRepository.save(imageEntity);
    }
}
