package com.sw4.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    PHOTO_NOT_FOUND(HttpStatus.NOT_FOUND, "사진을 찾을 수 없습니다."),
    LINK_NOT_FOUND(HttpStatus.NOT_FOUND, "공유 링크를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
