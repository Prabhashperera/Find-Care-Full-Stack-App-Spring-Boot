package org.app.findcarespringboot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    private String username;
    private String password;

//    @OneToMany(mappedBy = "user")
//    private List<FoundPost> foundPosts;

}
