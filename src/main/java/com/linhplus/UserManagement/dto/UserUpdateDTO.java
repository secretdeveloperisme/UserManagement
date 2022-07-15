package com.linhplus.UserManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UserUpdateDTO {
    @NotBlank(message = "name must not be empty")
    @Pattern(regexp = "^\\w{5,255}$", message = "username must not contain special characters or blank, length 5-255 characters")
    private String username;
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,16}$"
            , message = "password must have 8->16 character, contain number, lower and upper, special character")
    private String password;
    private Date dob;
    private String gender;
    private String address;
    private List<String> roles;
}
