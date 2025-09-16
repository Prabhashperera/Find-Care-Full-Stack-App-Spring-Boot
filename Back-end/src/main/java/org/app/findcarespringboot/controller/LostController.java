package org.app.findcarespringboot.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.app.findcarespringboot.dto.FilterPostsDto;
import org.app.findcarespringboot.dto.FoundPostDto;
import org.app.findcarespringboot.dto.LostPostDto;
import org.app.findcarespringboot.entity.LostPost;
import org.app.findcarespringboot.entity.User;
import org.app.findcarespringboot.service.AuthenticationService;
import org.app.findcarespringboot.dto.response.ApiResponseDto;
import org.app.findcarespringboot.service.LostPostService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("api/lost")
@CrossOrigin
@RequiredArgsConstructor
public class LostController {
    private final LostPostService lostPostService;
    private final Cloudinary cloudinary;
    private final AuthenticationService authenticationService;

    @PostMapping(path = "save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseDto> savePost(@RequestPart("dto") String dto, @RequestPart("file") MultipartFile file) throws IOException {
        log.info("Saving post endpoint reached");
        String photoUrl = null;
        System.out.println(dto);

        ObjectMapper mapper = new ObjectMapper();
        LostPostDto lostPostDto = mapper.readValue(dto, LostPostDto.class);
        User user = authenticationService.findById(lostPostDto.user());
        System.out.println(lostPostDto.address() + "1111111");

        if (user == null) {
            return ResponseEntity.ok(
                    new ApiResponseDto(500, "User Not Found", null)
            );
        }

        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        photoUrl = (String) uploadResult.get("secure_url");

        LostPost lostPost = new LostPost(
                user,
                lostPostDto.postDescription(),
                lostPostDto.petType(),
                lostPostDto.breed(),
                lostPostDto.color(),
                lostPostDto.gender(),
                photoUrl,
                lostPostDto.district(),
                lostPostDto.city(),
                lostPostDto.address(),
                lostPostDto.finderName(),
                lostPostDto.contactNumber(),
                lostPostDto.postDate(),
                lostPostDto.status()
        );

        LostPost savedPost = lostPostService.save(lostPost);

        if (savedPost != null) {
            return ResponseEntity.ok(new ApiResponseDto(200, "Save Success", photoUrl));
        }
        return ResponseEntity.ok(new ApiResponseDto(500, "Internal Server Error", null));
    }

    @PutMapping("update")
    public ResponseEntity<ApiResponseDto> updatePost(@RequestPart("dto") String dto, @RequestPart("file") MultipartFile file) throws IOException {
        System.out.println("Reach Controller");
        ObjectMapper mapper = new ObjectMapper();
        LostPostDto lostPostDto = mapper.readValue(dto, LostPostDto.class);

        LostPost existPost = lostPostService.findPostById(lostPostDto.postID());

        String publicIdFromCloudinary = lostPostService.extractPublicIdFromCloudinary(existPost.getPhotoUrl());
        Map updatedPhoto = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                "public_id", publicIdFromCloudinary,  // reuse old public_id
                "overwrite", true //overwrite the old link
        ));

        String updatedUrl = updatedPhoto.get("secure_url").toString();

        existPost.setPostDescription(lostPostDto.postDescription());
        existPost.setPetType(lostPostDto.petType());
        existPost.setBreed(lostPostDto.breed());
        existPost.setColor(lostPostDto.color());
        existPost.setGender(lostPostDto.gender());
        existPost.setPhotoUrl(updatedUrl);
        existPost.setDistrict(lostPostDto.district());
        existPost.setCity(lostPostDto.city());
        existPost.setAddress(lostPostDto.address());
        existPost.setFinderName(lostPostDto.finderName());
        existPost.setContactNumber(lostPostDto.contactNumber());
        existPost.setPostDate(lostPostDto.postDate());
        existPost.setStatus(lostPostDto.status());
        lostPostService.save(existPost);
        return ResponseEntity.ok(new ApiResponseDto(200, "Update Success", updatedUrl));

    }

    @DeleteMapping("delete/{postID}")
    public ResponseEntity<ApiResponseDto> deletePost(@PathVariable String postID) {
        boolean isDeleted = lostPostService.delete(postID);
        if (isDeleted) {
            return ResponseEntity.ok(new ApiResponseDto(200, "Delete Success", null));
        }
        return ResponseEntity.ok(new ApiResponseDto(500, "Internal Server Error", null));
    }

    @GetMapping("getall")
    public ResponseEntity<ApiResponseDto> getAllPosts() {
        List<LostPostDto> postList = lostPostService.getAll();
        if (!postList.isEmpty()) {
            return ResponseEntity.ok(
                    new ApiResponseDto(200, "Get All Success", postList)
            );
        }
        return ResponseEntity.ok(new ApiResponseDto(500, "Internal Server Error", null));
    }

    @PostMapping("filterpost")
    public ResponseEntity<ApiResponseDto> filterPost(@RequestBody FilterPostsDto filterDto) {
        List<LostPostDto> filteredPosts = lostPostService.filterPosts(filterDto);
        return ResponseEntity.ok(new ApiResponseDto(200, "Filter Posts Success", filteredPosts));
    }

    @GetMapping("loaduserpost/{userName}")
    public ResponseEntity<ApiResponseDto> loadUserPosts(@PathVariable String userName) {
        List<LostPostDto> userPosts = lostPostService.loadPostsByUser(userName);
        return ResponseEntity.ok(new ApiResponseDto(200, "Load Posts Success", userPosts));
    }

    @PutMapping("changestatus")
    public ResponseEntity<ApiResponseDto> changePostStatus(@RequestParam String postID, @RequestParam String status) {
        System.out.println("change Status Hit");
        boolean changeStatus = lostPostService.changeStatus(Integer.parseInt(postID), status);
        return ResponseEntity.ok(new ApiResponseDto(200, "Changed", changeStatus));
    }

}