package com.sw4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShareLinkResponse {
    private Long id;
    private String uuid;
    private String imageUrl;
    private String qrImageBase64;
}
