package org.app.findcarespringboot.entity;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class LostPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postID;
    @ManyToOne
    private User user;

    private String postDescription;
    private String petType;   // Dog, Cat, etc.
    private String breed;
    private String color;
    private String gender;

    private String photoUrl;  // Cloudinary image URL
    private String district;   // Colombo, Galle, Kandy
    private String city;       // Moratuwa, Dehiwala, Maharagama
    private String address;   // "Near bus stand", "Close to temple"

    private String finderName;
    private String contactNumber;

    private String postDate;
    private String status;

    public LostPost(User user, String postDescription, String petType, String breed, String color, String gender, String photoUrl, String district, String city, String address, String finderName, String contactNumber, String postDate, String status) {
        this.user = user;
        this.postDescription = postDescription;
        this.petType = petType;
        this.breed = breed;
        this.color = color;
        this.gender = gender;
        this.photoUrl = photoUrl;
        this.district = district;
        this.city = city;
        this.address = address;
        this.finderName = finderName;
        this.contactNumber = contactNumber;
        this.postDate = postDate;
        this.status = status;
    }
}

