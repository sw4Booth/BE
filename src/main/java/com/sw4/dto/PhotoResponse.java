package com.sw4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PhotoResponse {
    private Long id;
    private String imageUrl;
}
