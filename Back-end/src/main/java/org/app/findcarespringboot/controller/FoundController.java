package org.app.findcarespringboot.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.app.findcarespringboot.dto.FoundPostDto;
import org.app.findcarespringboot.entity.FoundPost;
import org.app.findcarespringboot.entity.User;
import org.app.findcarespringboot.service.AuthenticationService;
import org.app.findcarespringboot.service.FoundPostService;
import org.app.findcarespringboot.dto.response.ApiResponseDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("api/found")
@CrossOrigin
@RequiredArgsConstructor
public class FoundController {
    private final FoundPostService foundPostService;
    private final Cloudinary cloudinary;
    private final AuthenticationService authenticationService;

    @PostMapping(path = "save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseDto> savePost(@RequestPart("dto") String dto, @RequestPart("file") MultipartFile file) throws IOException {
        log.info("Saving post endpoint reached");
        String photoUrl = null;

        ObjectMapper mapper = new ObjectMapper();
        FoundPostDto foundPostDto = mapper.readValue(dto, FoundPostDto.class);
        User user = authenticationService.findById(foundPostDto.user());

        if (user == null) {
            return ResponseEntity.ok(
                    new ApiResponseDto(500, "User Not Found", null)
            );
        }

        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        photoUrl = (String) uploadResult.get("secure_url");

        FoundPost foundPost = new FoundPost(
                user,
                foundPostDto.postDescription(),
                foundPostDto.petType(),
                foundPostDto.breed(),
                foundPostDto.color(),
                foundPostDto.gender(),
                photoUrl,
                foundPostDto.district(),
                foundPostDto.city(),
                foundPostDto.landmark(),
                foundPostDto.finderName(),
                foundPostDto.contactNumber(),
                foundPostDto.postDate(),
                foundPostDto.status()
        );

        FoundPost savedPost = foundPostService.save(foundPost);

        if (savedPost != null) {
            return ResponseEntity.ok(new ApiResponseDto(200, "Save Success", photoUrl));
        }
        return ResponseEntity.ok(new ApiResponseDto(500, "Internal Server Error", null));
    }

    @PutMapping("update")
    public ResponseEntity<ApiResponseDto> updatePost(@RequestPart("dto") String dto, @RequestPart("file") MultipartFile file) throws IOException {
        System.out.println("Reach Controller");
        ObjectMapper mapper = new ObjectMapper();
        FoundPostDto foundPostDto = mapper.readValue(dto, FoundPostDto.class);

        FoundPost existPost = foundPostService.findPostById(foundPostDto.postID());

        String publicIdFromCloudinary = foundPostService.extractPublicIdFromCloudinary(existPost.getPhotoUrl());
        Map updatedPhoto = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                "public_id", publicIdFromCloudinary,  // reuse old public_id
                "overwrite", true //overwrite the old link
        ));

        String updatedUrl = updatedPhoto.get("secure_url").toString();

        existPost.setPostDescription(foundPostDto.postDescription());
        existPost.setPetType(foundPostDto.petType());
        existPost.setBreed(foundPostDto.breed());
        existPost.setColor(foundPostDto.color());
        existPost.setGender(foundPostDto.gender());
        existPost.setPhotoUrl(updatedUrl);
        existPost.setDistrict(foundPostDto.district());
        existPost.setCity(foundPostDto.city());
        existPost.setLandmark(foundPostDto.landmark());
        existPost.setFinderName(foundPostDto.finderName());
        existPost.setContactNumber(foundPostDto.contactNumber());
        existPost.setPostDate(foundPostDto.postDate());
        existPost.setStatus(foundPostDto.status());
        foundPostService.save(existPost);
        return ResponseEntity.ok(new ApiResponseDto(200, "Update Success", updatedUrl));

    }

    @DeleteMapping("delete/{postID}")
    public ResponseEntity<ApiResponseDto> deletePost(@PathVariable String postID) {
        boolean isDeleted = foundPostService.delete(postID);
        if (isDeleted) {
            return ResponseEntity.ok(new ApiResponseDto(200, "Delete Success", null));
        }
        return ResponseEntity.ok(new ApiResponseDto(500, "Internal Server Error", null));
    }

    @GetMapping("getall")
    public ResponseEntity<ApiResponseDto> getAllPosts() {
        List<FoundPostDto> postList = foundPostService.getAll();
        if (!postList.isEmpty()) {
            return ResponseEntity.ok(
                    new ApiResponseDto(200, "Get All Success", postList)
            );
        }
        return ResponseEntity.ok(new ApiResponseDto(500, "Internal Server Error", null));
    }

}