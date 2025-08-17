package org.app.findcarespringboot.dto;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.app.findcarespringboot.entity.User;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FoundPostDto {
    private int postID;
    private User user;

    private String postDescription;
    private String petType;   // Dog, Cat, etc.
    private String breed;
    private String color;
    private String gender;

    private String photoUrl;  // Cloudinary image URL
    private String district;   // Colombo, Galle, Kandy
    private String city;       // Moratuwa, Dehiwala, Maharagama
    private String landmark;   // "Near bus stand", "Close to temple"

    private String finderName;
    private String contactNumber;
    private String email;

    private String postDate;
    private String status;
    private String mobileNumber;
}
