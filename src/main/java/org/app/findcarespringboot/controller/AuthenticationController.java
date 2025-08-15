package org.app.findcarespringboot.controller;

import lombok.RequiredArgsConstructor;
import org.app.findcarespringboot.entity.User;
import org.app.findcarespringboot.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("login")
    public String userLogin(@RequestBody User user) {
        boolean isExist = authenticationService.loginUser(user);
        if (isExist) {
            return "Login successful";
        }
        return "Login failed";
    }

    @PostMapping("signup")
    public String userRegister(@RequestBody User user) {
        User savedUser = authenticationService.saveUser(user);
        return savedUser.toString();
    }
}
