package com.linhplus.UserManagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ResponseErrorDTO {
    private String message;
    private Map<String, Object> errors;
}
