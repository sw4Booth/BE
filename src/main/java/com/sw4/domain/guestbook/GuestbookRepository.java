package com.sw4.domain.guestbook;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestbookRepository extends JpaRepository<Guestbook, Long> {

    Optional<Guestbook> findByPhotoId(Long photoId);
}
