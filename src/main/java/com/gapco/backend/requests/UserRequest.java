package com.gapco.backend.requests;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String name;
    private String email;
    private String mobileNumber;
    private Boolean active;
}
