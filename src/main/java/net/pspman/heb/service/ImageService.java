package net.pspman.heb.service;

import javax.persistence.EntityNotFoundException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.Feature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.pspman.heb.model.ImageEntity;
import net.pspman.heb.model.ImageRequest;
import net.pspman.heb.repository.ImageRepository;
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
        log.debug("Getting images with objects : {}", objects);
        return imageRepository.findAllByObjects(objects);
    }

    public ImageEntity getImageById(BigInteger id){
        log.debug("Getting image with id : {}", id);
        Optional<ImageEntity> image = imageRepository.findById(id);

        if(!image.isPresent()){
            throw new EntityNotFoundException("Unable to find image with id : " + id);
        }

        return image.get();
    }

    public ImageEntity processImage(ImageRequest request) {
        Resource resource = loader.getResource(request.getUrl());

        ImageEntity imageEntity = new ImageEntity(request);

        if(request.isEnableDetection()){
            log.debug("Running Object detection");
            AnnotateImageResponse response = cloudVisionTemplate.analyzeImage(resource, Feature.Type.LABEL_DETECTION);
            response.getLabelAnnotationsList().forEach(label -> imageEntity.addLabel(label.getDescription()));
        }

        return imageRepository.save(imageEntity);
    }
}
