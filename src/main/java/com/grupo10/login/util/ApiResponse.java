package com.grupo10.login.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {

    private int code;
    private T data;
    private String message;

}
