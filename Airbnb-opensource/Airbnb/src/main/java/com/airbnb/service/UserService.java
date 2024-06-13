package com.airbnb.service;

import com.airbnb.payload.LoginDTO;
import com.airbnb.payload.UserDTO;
import org.springframework.stereotype.Service;

public interface UserService {
    UserDTO addUser(UserDTO userDTO);

    String login(LoginDTO loginDTO);
    String verifyOAuth(String email, String name);
}
