package com.airbnb.payload;

import lombok.Data;

@Data
public class JWTResponse {
    private String type="Bearer";
    private String token;
}
