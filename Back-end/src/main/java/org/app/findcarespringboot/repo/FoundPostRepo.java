package org.app.findcarespringboot.repo;

import org.app.findcarespringboot.dto.FoundPostDto;
import org.app.findcarespringboot.entity.FoundPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoundPostRepo extends JpaRepository<FoundPost, Integer> {
    List<FoundPost> getFoundPostByPostID(int postID);

    @Query("SELECT f FROM FoundPost f " +
            "WHERE (:petType IS NULL OR f.petType = :petType) " +
            "AND (:status IS NULL OR f.status = :status) " +
            "AND (:district IS NULL OR f.district = :district) " +
            "AND (:city IS NULL OR f.city = :city)")
    List<FoundPost> filterPosts(
            @Param("petType") String petType,
            @Param("status") String status,
            @Param("district") String district,
            @Param("city") String city
    );
}
