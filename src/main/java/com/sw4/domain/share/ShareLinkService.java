package com.sw4.domain.share;

import com.sw4.domain.photo.Photo;
import com.sw4.domain.photo.PhotoService;
import com.sw4.dto.ShareLinkRequest;
import com.sw4.dto.ShareLinkResponse;
import com.sw4.exception.CustomException;
import com.sw4.exception.ErrorCode;
import com.sw4.util.QrCodeUtil;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
public class ShareLinkService {

    private final ShareLinkRepository repo;
    private final PhotoService photoService;

    @Value("${app.share.base-url}")
    private String baseUrl;

    public ShareLinkResponse create(ShareLinkRequest req) {
        Photo photo = photoService.findById(req.getPhotoId());
        Optional<ShareLink> existing = repo.findByPhotoId(req.getPhotoId());

        ShareLink linkToUse;
        if (existing.isPresent()) {
            linkToUse = existing.get();
        } else {
            ShareLink newLink = new ShareLink();
            newLink.setUuid(UUID.randomUUID().toString());
            newLink.setPhoto(photo);
            linkToUse = repo.save(newLink);
        }

        String url = baseUrl + linkToUse.getUuid();
        String qr = QrCodeUtil.generateQrCodeBase64(url, 300, 300);
        return new ShareLinkResponse(linkToUse.getId(), linkToUse.getUuid(), photo.getImageUrl(), qr);
    }

    public ShareLinkResponse get(String uuid) {
        ShareLink link = repo.findByUuid(uuid)
                .orElseThrow(() -> new CustomException(ErrorCode.LINK_NOT_FOUND));
        String url = baseUrl + link.getUuid();
        String qr = QrCodeUtil.generateQrCodeBase64(url, 300, 300);
        return new ShareLinkResponse(link.getId(), link.getUuid(), link.getPhoto().getImageUrl(), qr);
    }
}
