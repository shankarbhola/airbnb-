package com.airbnb.controller;

import com.airbnb.entity.User;
import com.airbnb.payload.JWTResponse;
import com.airbnb.payload.LoginDTO;
import com.airbnb.payload.UserDTO;
import com.airbnb.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/adduser")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UserDTO dto = userService.addUser(userDTO);
        if (dto != null){
            return new ResponseEntity<>(dto,HttpStatus.CREATED);
        }
        return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String token = userService.login(loginDTO);
        if (token != null) {
            JWTResponse response = new JWTResponse();
            response.setToken(token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<String>("Invalid Credentials", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getCurrentProfile(@AuthenticationPrincipal User user){
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/loginOauth")
    public ResponseEntity<?> getUserDetails(@AuthenticationPrincipal OAuth2User oauth2User) {
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");
        String token = userService.verifyOAuth(email, name);
        JWTResponse response = new JWTResponse();
        response.setToken(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
