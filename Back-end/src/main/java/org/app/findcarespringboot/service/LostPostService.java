package org.app.findcarespringboot.service;

import org.app.findcarespringboot.dto.FilterPostsDto;
import org.app.findcarespringboot.dto.FoundPostDto;
import org.app.findcarespringboot.dto.LostPostDto;
import org.app.findcarespringboot.entity.FoundPost;
import org.app.findcarespringboot.entity.LostPost;

import java.util.List;

public interface LostPostService {
    LostPost save(LostPost lostPost);
    LostPost findPostById(int postID);
    String extractPublicIdFromCloudinary(String url);
    boolean delete(String postID);
    List<LostPostDto> getAll();
    List<LostPostDto> filterPosts(FilterPostsDto filterDto);
}
