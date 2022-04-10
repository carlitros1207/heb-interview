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
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "image")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private BigInteger id;

    @Column
    private String url;

    @Column
    private String label;

    @JsonIgnore
    @OneToMany(mappedBy = "imageEntity",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<DetectedObjectEntity> detectedObjects = new HashSet<>();

    @JsonGetter
    public List<String> getObjects(){
        return detectedObjects.stream()
                .map(DetectedObjectEntity::getName)
                .collect(Collectors.toList());
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
        detectedObjectEntity.setName(label);
        this.detectedObjects.add(detectedObjectEntity);
        detectedObjectEntity.setImageEntity(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ImageEntity that = (ImageEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
