package com.airbnb.service;

import com.airbnb.entity.Role;
import com.airbnb.entity.User;
import com.airbnb.payload.LoginDTO;
import com.airbnb.payload.RoleDTO;
import com.airbnb.payload.UserDTO;
import com.airbnb.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private JWTService jwtService;

    public UserServiceImpl(UserRepository userRepository, JWTService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public UserDTO addUser(UserDTO userDTO) {
        Optional<User> checkUser = userRepository.findByUsernameOrEmailId(userDTO.getUsername(), userDTO.getEmailId());
        if(checkUser.isEmpty()) {
            User user = dtoToUser(userDTO);
            User saveduser = userRepository.save(user);
            UserDTO dto = userToDTO(saveduser);
            return dto;
        }
        return null;
    }

    @Override
    public String login(LoginDTO loginDTO) {
        Optional<User> opUser = userRepository.findByUsernameOrEmailId(loginDTO.getUsername(), loginDTO.getEmailId());
        if (opUser.isPresent()) {
            User user = opUser.get();
            if (BCrypt.checkpw(loginDTO.getPassword(),user.getPassword())) {
                String token = jwtService.generateToken(user);
                return token;
            }
        }
        return null;
    }

    public UserDTO userToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmailId(user.getEmailId());
        userDTO.setUsername(user.getUsername());
        return userDTO;
    }

    public User dtoToUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmailId(userDTO.getEmailId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        Role role = new Role();
        role.setId(userDTO.getRole().getId());
        role.setRoleName(userDTO.getRole().getRoleName());
        user.setRole(role);
        return user;
    }
    @Override
    public String verifyOAuth(String email, String name) {
        User checkUser = userRepository.findByEmailId(email);
        if(checkUser==null){
            User appUser = new User();
            appUser.setName(name);
            appUser.setUsername(email);
            appUser.setEmailId(email);
            Role role = new Role();
            role.setRoleName("ROLE_USER");
            appUser.setRole(role);
            appUser.setPassword(new BCryptPasswordEncoder().encode(email));
            User savedUsed = userRepository.save(appUser);
            return jwtService.generateToken(savedUsed);
        }
        return jwtService.generateToken(checkUser);

    }
}
