package net.pspman.heb.controller;

import javax.ws.rs.BadRequestException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.pspman.heb.model.ImageEntity;
import net.pspman.heb.model.ImageRequest;
import net.pspman.heb.service.ImageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;


    @GetMapping()
    public ResponseEntity<List<ImageEntity>> getImages(@Param("objects") String objects){
        List<ImageEntity> images;
        if(StringUtils.isBlank(objects)){
            log.debug("Getting all images");
            images = imageService.getAllImages();
        }else {
            log.debug("Getting images that contain objects : {}", objects);
            images = imageService.getImagesByObjects(Arrays.asList(objects.split(",")));
        }
        return ResponseEntity.ok(images);
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<?> getSingleImage( @PathVariable BigInteger imageId){
        log.debug("Getting image with ID : {}", imageId);

        ImageEntity image = imageService.getImageById(imageId);
        log.debug("Returning image with ID : {}", imageId);

        return ResponseEntity.ok(image);
    }

    @PostMapping
    public ResponseEntity<ImageEntity> postProcessImage(@RequestBody ImageRequest imageRequest) {
        if (!StringUtils.isNotBlank(imageRequest.getUrl())) {
            throw new BadRequestException("Missing url");
        }

        log.debug("Processing new image with URL : {}", imageRequest.getUrl());
        ImageEntity image = imageService.processImage(imageRequest);
        return ResponseEntity.ok(image);
    }
}
