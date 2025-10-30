package com.sw4.domain.photo;

import com.sw4.exception.CustomException;
import com.sw4.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final S3Service s3Service;

    public Photo upload(MultipartFile file) {
        String url = s3Service.uploadFile(file);
        Photo p = new Photo();
        p.setImageUrl(url);
        return photoRepository.save(p);
    }

    public Photo findById(Long id) {
        return photoRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PHOTO_NOT_FOUND));
    }

    public void delete(Long id) {
        photoRepository.deleteById(id);
    }
}
