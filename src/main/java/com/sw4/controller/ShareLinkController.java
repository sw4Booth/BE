package com.sw4.controller;

import com.sw4.domain.share.ShareLinkService;
import com.sw4.dto.ShareLinkRequest;
import com.sw4.dto.ShareLinkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/share")
@RequiredArgsConstructor
public class ShareLinkController {

    private final ShareLinkService service;

    @PostMapping("/create")
    public ShareLinkResponse create(@RequestBody ShareLinkRequest req) {
        return service.create(req);
    }

    @GetMapping("/{uuid}")
    public ShareLinkResponse get(@PathVariable String uuid) {
        return service.get(uuid);
    }
}
