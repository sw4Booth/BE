package com.sw4.domain.photo;

import com.sw4.domain.guestbook.GuestbookRepository;
import com.sw4.domain.share.ShareLinkRepository;
import com.sw4.exception.CustomException;
import com.sw4.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final S3Service s3Service;
    private final GuestbookRepository guestbookRepository;
    private final ShareLinkRepository shareLinkRepository;

    @Transactional
    public Photo upload(MultipartFile file) {
        String url = s3Service.uploadFile(file);
        Photo p = new Photo();
        p.setImageUrl(url);
        return photoRepository.save(p);
    }

    @Transactional(readOnly = true)
    public Photo findById(Long id) {
        return photoRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PHOTO_NOT_FOUND));
    }

    @Transactional
    public void delete(Long id) {
        Photo photo = findById(id);

        String imageUrl = photo.getImageUrl();
        guestbookRepository.findByPhotoId(id).ifPresent(guestbookRepository::delete);
        shareLinkRepository.findByPhotoId(id).ifPresent(shareLinkRepository::delete);

        s3Service.deleteFile(imageUrl);
        photoRepository.delete(photo);
    }
}
