package org.app.findcarespringboot.repo;

import org.app.findcarespringboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    boolean existsUserByUsernameAndPassword(String username, String password);

    boolean existsUserByUsername(String username);

    User findByUsername(String username);
}
