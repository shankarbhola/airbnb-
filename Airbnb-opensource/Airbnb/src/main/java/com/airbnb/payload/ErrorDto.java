package com.airbnb.payload;

import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Getter
public class ErrorDto {
    private String message;
    private Date date;
    private String description;

    public ErrorDto(String message, Date date, String description) {
        this.message = message;
        this.date = date;
        this.description = description;
    }
}
