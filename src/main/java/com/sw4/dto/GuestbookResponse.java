package com.sw4.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GuestbookResponse {
    private Long id;
    private String imageUrl;
    private LocalDateTime createdAt;
    private String message;
}
