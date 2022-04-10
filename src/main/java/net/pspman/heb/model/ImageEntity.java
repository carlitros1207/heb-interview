package net.pspman.heb.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

@Data
@Entity
@Table(name = "image")
@NoArgsConstructor
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private BigInteger id;

    @Column
    private String url;

    @Column
    private String label;

    @OneToMany(mappedBy = "imageEntity",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<DetectedObjectEntity> detectedObjects = new HashSet<>();

    @JsonGetter
    public List<String> getObjects(){
        return detectedObjects.stream().map(DetectedObjectEntity::getObject).collect(Collectors.toList());
    }

    public ImageEntity(ImageRequest request) {
        this.url = request.getUrl();
        if(StringUtils.isNotBlank(request.getLabel())){
            this.label = request.getLabel();
        } else {
            this.label = RandomStringUtils.randomAlphabetic(32);
        }
    }

    public void addLabel(String label){
        DetectedObjectEntity detectedObjectEntity = new DetectedObjectEntity();
        detectedObjectEntity.setObject(label);
        this.detectedObjects.add(detectedObjectEntity);
        detectedObjectEntity.setImageEntity(this);
    }

}
