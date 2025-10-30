package com.sw4.controller;

import com.sw4.domain.guestbook.GuestbookService;
import com.sw4.dto.GuestbookResponse;
import com.sw4.dto.GuestbookRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/guestbook")
@RequiredArgsConstructor
public class GuestbookController implements GuestbookControllerDocs {

    private final GuestbookService service;

    @PostMapping
    public GuestbookResponse create(@RequestBody GuestbookRequest req) {
        return service.create(req);
    }

    @GetMapping
    public Page<GuestbookResponse> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }
}
