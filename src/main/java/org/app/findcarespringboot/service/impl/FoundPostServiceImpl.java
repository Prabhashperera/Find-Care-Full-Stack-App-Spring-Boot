package org.app.findcarespringboot.service.impl;

import lombok.RequiredArgsConstructor;
import org.app.findcarespringboot.entity.FoundPost;
import org.app.findcarespringboot.repo.FoundPostRepo;
import org.app.findcarespringboot.service.FoundPostService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoundPostServiceImpl implements FoundPostService {
    private FoundPostRepo foundPostRepo;

    @Override
    public FoundPost save(FoundPost foundPost) {
        return foundPostRepo.save(foundPost);
    }
}
