package org.app.findcarespringboot.controller;

import lombok.RequiredArgsConstructor;
import org.app.findcarespringboot.entity.User;
import org.app.findcarespringboot.service.AuthenticationService;
import org.app.findcarespringboot.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtUtil jwtUtil;

    @PostMapping("login")
    public String userLogin(@RequestBody User user) {
        boolean isExist = authenticationService.loginUser(user);
        if (isExist) {
            return jwtUtil.generateToken(user.getUsername());
        }
        return "Login failed";
    }

    @PostMapping("signup")
    public String userRegister(@RequestBody User user) {
        User savedUser = authenticationService.saveUser(user);
        if (savedUser != null) {
            return "User registered successfully";
        }
        return "User not registered";
    }
}
