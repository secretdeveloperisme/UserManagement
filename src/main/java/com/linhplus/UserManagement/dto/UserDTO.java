package com.linhplus.UserManagement.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
public class UserDTO {
    private String username;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dob;
    private String gender;
    private String address;
    private List<String> roles;
}
