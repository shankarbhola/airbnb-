package com.airbnb.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDTO {


    @Email(message = "Email should be valid")
    private String emailId;

    private String username;

    @NotNull(message = "Password cannot be null")
    private String password;
}
