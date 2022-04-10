package net.pspman.heb.model;

import lombok.Data;

@Data
public class ImageRequest {

    private String url;

    private String label;

    private boolean enableDetection;
}
