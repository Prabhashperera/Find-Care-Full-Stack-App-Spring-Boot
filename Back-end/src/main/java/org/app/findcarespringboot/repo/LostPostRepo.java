package org.app.findcarespringboot.repo;

import org.app.findcarespringboot.dto.FoundPostDto;
import org.app.findcarespringboot.entity.FoundPost;
import org.app.findcarespringboot.entity.LostPost;
import org.app.findcarespringboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LostPostRepo extends JpaRepository<LostPost, Integer> {
    List<LostPost> getLostPostByPostID(int postID);

    @Query("SELECT f FROM LostPost f " +
            "WHERE (:petType IS NULL OR :petType = '' OR f.petType = :petType) " +
            "AND (:status IS NULL OR :status = '' OR f.status = :status) " +
            "AND (:district IS NULL OR :district = '' OR f.district = :district) " +
            "AND (:city IS NULL OR :city = '' OR f.city = :city)")
    List<LostPost> filterPosts(
            @Param("petType") String petType,
            @Param("status") String status,
            @Param("district") String district,
            @Param("city") String city
    );

    List<LostPost> getLostPostByUser(User user);
}

