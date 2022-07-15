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
public class UserCreationDTO {
    @NotBlank(message = "name must not be empty")
    @Pattern(regexp = "^[\\w.]{5,255}$", message = "username must not contain special characters or blank, length 5-255 characters")
    private String username;
    @NotBlank(message = "password must no be empty")
    @Pattern(regexp = "(?=.*[^\\w\\d\\s:]).*", message = "password must have special characters" )
    @Pattern(regexp = "(?=.*\\d).*", message = "password must have digits" )
    @Pattern(regexp = "(?=.*[A-Z])+.*", message = "password must have upper characters" )
    @Pattern(regexp = "(?=.*[a-z]).*", message = "password must have lower character" )
    @Pattern(regexp = "([^\\s]).*", message = "password must not have blank character" )
    @Pattern(regexp = ".{8,16}", message = "password must have 8-16 characters" )
    private String password;
    private Date dob;
    private String gender;
    private String address;
    @NotEmpty
    private List<String> roles;
}
