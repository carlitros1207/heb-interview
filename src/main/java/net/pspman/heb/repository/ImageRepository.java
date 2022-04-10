package net.pspman.heb.repository;

import java.math.BigInteger;
import java.util.List;

import net.pspman.heb.model.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImageRepository extends JpaRepository<ImageEntity, BigInteger> {

    @Query(value = "select * from image left join detected_object d on image.id = d.image_id where d.name in :objects",nativeQuery = true)
    List<ImageEntity> findAllByObjects(@Param("objects") List<String> objects);
}
