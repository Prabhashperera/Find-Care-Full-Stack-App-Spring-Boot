package org.app.findcarespringboot.service.impl;

import lombok.RequiredArgsConstructor;
import org.app.findcarespringboot.entity.FoundPost;
import org.app.findcarespringboot.repo.FoundPostRepo;
import org.app.findcarespringboot.repo.UserRepo;
import org.app.findcarespringboot.service.FoundPostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FoundPostServiceImpl implements FoundPostService {
    private final FoundPostRepo foundPostRepo;

    @Override
    public FoundPost save(FoundPost foundPost) {
        try {
            return foundPostRepo.save(foundPost);
        } catch (Exception e) {
            System.err.println("Failed to save FoundPost: " + e.getMessage());
            return null; // or throw a custom exception
        }
    }
}
