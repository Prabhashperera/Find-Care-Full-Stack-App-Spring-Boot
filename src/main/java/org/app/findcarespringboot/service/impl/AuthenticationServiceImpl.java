package org.app.findcarespringboot.service.impl;

import lombok.RequiredArgsConstructor;
import org.app.findcarespringboot.entity.User;
import org.app.findcarespringboot.exception.DataAlreadyExistsException;
import org.app.findcarespringboot.repo.UserRepo;
import org.app.findcarespringboot.service.AuthenticationService;
import org.app.findcarespringboot.util.JwtUtil;
import org.springframework.security.authentication.BadCredentialsException;
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
    private final JwtUtil jwtUtil;

    @Override
    public User saveUser(User user) {
        boolean isExist = userRepo.existsUserByUsername(user.getUsername()); //check if there is Exist user
        if (!isExist) {
            User toSave = new User(); //new Object for Hashed Password and Plain Username
            toSave.setUsername(user.getUsername());
            toSave.setPassword(passwordEncoder.encode(user.getPassword())); //Password Converting to the hashed Version
            return userRepo.save(toSave);
        }
        throw new DataAlreadyExistsException("User with username " + user.getUsername() + " already exists");
    }

    @Override
    public String loginUser(User user) {
        User foundUser = userRepo.findByUsername(user.getUsername());
        if (foundUser != null) {
            boolean matches = passwordEncoder.matches(user.getPassword(), foundUser.getPassword());
            if (matches) {
                return jwtUtil.generateToken(user.getUsername()); //Generating JWT Token
            }
        }
        throw new BadCredentialsException("Invalid username or password");
    }
}
