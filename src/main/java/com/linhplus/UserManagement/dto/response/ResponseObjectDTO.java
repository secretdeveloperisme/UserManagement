package com.linhplus.UserManagement.dto.response;


import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ResponseObjectDTO {
    private String message;
    private Map<String, Object> data;
}
