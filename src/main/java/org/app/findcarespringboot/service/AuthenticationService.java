package org.app.findcarespringboot.service;


import org.app.findcarespringboot.entity.User;

public interface AuthenticationService {
    User saveUser(User user);
}
