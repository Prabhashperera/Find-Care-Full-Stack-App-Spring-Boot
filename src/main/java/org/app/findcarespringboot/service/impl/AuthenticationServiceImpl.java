package org.app.findcarespringboot.service.impl;

import lombok.RequiredArgsConstructor;
import org.app.findcarespringboot.entity.User;
import org.app.findcarespringboot.exception.DataAlreadyExistsException;
import org.app.findcarespringboot.repo.UserRepo;
import org.app.findcarespringboot.service.AuthenticationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        boolean isExist = userRepo.existsUserByUsername(user.getUsername());
        if (isExist) {
            throw new DataAlreadyExistsException("User with username " + user.getUsername() + " already exists");
        }
        return userRepo.save(user);
    }

    @Override
    public boolean loginUser(User user) {
        User foundUser = userRepo.findByUsername(user.getUsername());
        if (foundUser != null) {
            return passwordEncoder.matches(user.getPassword(), foundUser.getPassword());
        }
        return false;
    }
}
