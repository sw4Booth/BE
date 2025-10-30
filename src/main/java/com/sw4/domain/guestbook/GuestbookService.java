package com.sw4.domain.guestbook;

import com.sw4.domain.photo.Photo;
import com.sw4.domain.photo.PhotoService;
import com.sw4.dto.GuestbookRequest;
import com.sw4.dto.GuestbookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GuestbookService {

    private final GuestbookRepository repo;
    private final PhotoService photoService;

    public GuestbookResponse create(GuestbookRequest req) {
        Photo photo = photoService.findById(req.getPhotoId());
        Guestbook g = new Guestbook();
        g.setPhoto(photo);
        g.setMessage(req.getMessage());
        Guestbook saved = repo.save(g);
        return new GuestbookResponse(saved.getId(), saved.getPhoto().getImageUrl(), saved.getCreatedAt(),
                saved.getMessage());
    }

    @Transactional(readOnly = true)
    public Page<GuestbookResponse> findAll(Pageable pageable) {
        return repo.findAll(pageable)
                .map(g -> new GuestbookResponse(g.getId(), g.getPhoto().getImageUrl(), g.getCreatedAt(),
                        g.getMessage()));
    }
}
