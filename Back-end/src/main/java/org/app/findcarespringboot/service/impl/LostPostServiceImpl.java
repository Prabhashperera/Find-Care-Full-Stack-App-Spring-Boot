package org.app.findcarespringboot.service.impl;

import lombok.RequiredArgsConstructor;
import org.app.findcarespringboot.dto.FilterPostsDto;
import org.app.findcarespringboot.dto.LostPostDto;
import org.app.findcarespringboot.entity.LostPost;
import org.app.findcarespringboot.entity.User;
import org.app.findcarespringboot.exception.InternalServerErrorException;
import org.app.findcarespringboot.repo.LostPostRepo;
import org.app.findcarespringboot.repo.UserRepo;
import org.app.findcarespringboot.service.LostPostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LostPostServiceImpl implements LostPostService {
    private final LostPostRepo lostPostRepo;
    private final UserRepo userRepo;

    @Override
    public LostPost save(LostPost lostPost) {
        try {
            return lostPostRepo.save(lostPost);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error saving Lost post");
        }
    }

    @Override
    public LostPost findPostById(int postID) {
        List<LostPost> lostPostByPostID = lostPostRepo.getLostPostByPostID(postID);
        if (!lostPostByPostID.isEmpty()) {
            return lostPostByPostID.get(0);
        }
        throw new NullPointerException("Post not found");
    }

//    Customer Method for Find the public id from exist uploaded url
    public String extractPublicIdFromCloudinary(String url) {
        String path = url.substring(url.indexOf("/upload/") + 8); // remove prefix
        if (path.contains("/v")) {
            path = path.substring(path.indexOf("/") + 1); // remove version
        }
        path = path.substring(0, path.lastIndexOf(".")); // remove extension
        System.out.println(path);
        return path; // just the public_id
    }

    @Override
    public boolean delete(String postID) {
        Optional<LostPost> byId = lostPostRepo.findById(Integer.valueOf(postID));
        if (byId.isPresent()) {
            lostPostRepo.delete(byId.get());
            return true;
        }
        throw new NullPointerException("Post not found");
    }
    @Override
    public List<LostPostDto> getAll() {
        return lostPostRepo.findAll()
                .stream()
                .map(lostPost -> new LostPostDto(
                        lostPost.getPostID(),
                        lostPost.getUser() != null ? lostPost.getUser().getUsername() : null,
                        lostPost.getPostDescription(),
                        lostPost.getPetType(),
                        lostPost.getBreed(),
                        lostPost.getColor(),
                        lostPost.getGender(),
                        lostPost.getPhotoUrl(),
                        lostPost.getDistrict(),
                        lostPost.getCity(),
                        lostPost.getAddress(),
                        lostPost.getFinderName(),
                        lostPost.getContactNumber(),
                        lostPost.getPostDate(),
                        lostPost.getStatus()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<LostPostDto> filterPosts(FilterPostsDto dto) {
        List<LostPost> foundPosts = lostPostRepo.filterPosts(
                dto.getPetType(), dto.getStatus(), dto.getDistrict(), dto.getCity()
        );
        List<LostPostDto> filteredPosts = new ArrayList<>();
        for (LostPost foundPost : foundPosts) {
            LostPostDto lostPostDto = new LostPostDto(
                    foundPost.getPostID(),
                    foundPost.getUser() != null ? foundPost.getUser().getUsername() : null,
                    foundPost.getPostDescription(),
                    foundPost.getPetType(),
                    foundPost.getBreed(),
                    foundPost.getColor(),
                    foundPost.getGender(),
                    foundPost.getPhotoUrl(),
                    foundPost.getDistrict(),
                    foundPost.getCity(),
                    foundPost.getAddress(),
                    foundPost.getFinderName(),
                    foundPost.getContactNumber(),
                    foundPost.getPostDate(),
                    foundPost.getStatus()
            );
            filteredPosts.add(lostPostDto);
        }
        return filteredPosts;
    }

    @Override
    public List<LostPostDto> loadPostsByUser(String userName) {
        List<User> userByUsername = userRepo.getUserByUsername(userName);
        List<LostPost> posts = lostPostRepo.getLostPostByUser(userByUsername.get(0));
        List<LostPostDto> dtos = new ArrayList<>();
        for (LostPost lostPost : posts) {
            LostPostDto lostPostDto = new LostPostDto(
                    lostPost.getPostID(),
                    lostPost.getUser().getUsername(),     // assuming you want username here
                    lostPost.getPostDescription(),
                    lostPost.getPetType(),
                    lostPost.getBreed(),
                    lostPost.getColor(),
                    lostPost.getGender(),
                    lostPost.getPhotoUrl(),
                    lostPost.getDistrict(),
                    lostPost.getCity(),
                    lostPost.getAddress(),
                    lostPost.getFinderName(),
                    lostPost.getContactNumber(),
                    lostPost.getPostDate(),
                    lostPost.getStatus()
            );
            dtos.add(lostPostDto);
        }
        return dtos;
    }
}
