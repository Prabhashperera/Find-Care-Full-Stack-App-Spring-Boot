package org.app.findcarespringboot.repo;

import org.app.findcarespringboot.entity.FoundPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoundPostRepo extends JpaRepository<FoundPost, Integer> {
}
