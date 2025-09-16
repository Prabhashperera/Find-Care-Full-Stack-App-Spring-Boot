package org.app.findcarespringboot.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.app.findcarespringboot.dto.FilterPostsDto;
import org.app.findcarespringboot.dto.FoundPostDto;
import org.app.findcarespringboot.entity.FoundPost;
import org.app.findcarespringboot.entity.User;
import org.app.findcarespringboot.exception.InternalServerErrorException;
import org.app.findcarespringboot.repo.FoundPostRepo;
import org.app.findcarespringboot.repo.UserRepo;
import org.app.findcarespringboot.service.FoundPostService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FoundPostServiceImpl implements FoundPostService {
    private final FoundPostRepo foundPostRepo;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    @Override
    public FoundPost save(FoundPost foundPost) {
        try {
            return foundPostRepo.save(foundPost);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error saving found post");
        }
    }

    @Override
    public FoundPost findPostById(int postID) {
        List<FoundPost> foundPostByPostID = foundPostRepo.getFoundPostByPostID(postID);
        if (!foundPostByPostID.isEmpty()) {
            return foundPostByPostID.get(0);
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
        Optional<FoundPost> byId = foundPostRepo.findById(Integer.valueOf(postID));
        if (byId.isPresent()) {
            foundPostRepo.delete(byId.get());
            return true;
        }
        throw new NullPointerException("Post not found");
    }
    @Override
    public List<FoundPostDto> getAll() {
        return foundPostRepo.findAll()
                .stream()
                .map(foundPost -> new FoundPostDto(
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
                        foundPost.getLandmark(),
                        foundPost.getFinderName(),
                        foundPost.getContactNumber(),
                        foundPost.getPostDate(),
                        foundPost.getStatus()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<FoundPostDto> filterPosts(FilterPostsDto dto) {
        List<FoundPost> foundPosts = foundPostRepo.filterPosts(
                dto.getPetType(), dto.getStatus(), dto.getDistrict(), dto.getCity()
        );
        List<FoundPostDto> filteredPosts = new ArrayList<>();
        for (FoundPost foundPost : foundPosts) {
            FoundPostDto foundPostDto = new FoundPostDto(
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
                    foundPost.getLandmark(),
                    foundPost.getFinderName(),
                    foundPost.getContactNumber(),
                    foundPost.getPostDate(),
                    foundPost.getStatus()
            );
            filteredPosts.add(foundPostDto);
        }
        return filteredPosts;
    }

    @Override
    public List<FoundPostDto> loadPostsByUser(String userName) {
        List<User> userByUsername = userRepo.getUserByUsername(userName);
        List<FoundPost> posts = foundPostRepo.getFoundPostsByUser(userByUsername.get(0));
        List<FoundPostDto> dtos = new ArrayList<>();
        for (FoundPost foundPost : posts) {
            FoundPostDto foundPostDto = new FoundPostDto(
                    foundPost.getPostID(),
                    foundPost.getUser().getUsername(),     // assuming you want username here
                    foundPost.getPostDescription(),
                    foundPost.getPetType(),
                    foundPost.getBreed(),
                    foundPost.getColor(),
                    foundPost.getGender(),
                    foundPost.getPhotoUrl(),
                    foundPost.getDistrict(),
                    foundPost.getCity(),
                    foundPost.getLandmark(),
                    foundPost.getFinderName(),
                    foundPost.getContactNumber(),
                    foundPost.getPostDate(),
                    foundPost.getStatus()
            );
            dtos.add(foundPostDto);
        }
        return dtos;
    }

    @Override
    public boolean changeStatus(int postId, String status) {
        System.out.println("Service Hits" + postId);
        Optional<FoundPost> byId = foundPostRepo.findById(postId);
        byId.ifPresent(foundPost -> foundPost.setStatus(status));
        return true;
    }
}
