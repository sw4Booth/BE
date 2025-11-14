package com.sw4.controller;

import com.sw4.dto.PhotoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "사진 (Photo)", description = "S3에 사진 파일을 업로드하고 ID를 반환합니다.")
public interface PhotoControllerDocs {

    @Operation(summary = "사진 파일 업로드", description = "파일을 S3에 저장하고, DB에 기록된 Photo ID와 URL을 반환합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "업로드 및 등록 성공",
                    content = @Content(
                            schema = @Schema(implementation = PhotoResponse.class),
                            examples = @ExampleObject(
                                    name = "사진 업로드 성공 예시",
                                    value = """
                                            {
                                              "id": 1,
                                              "imageUrl": "https://sw4booth.s3.ap-northeast-2.amazonaws.com/uuid_photo.png"
                                            }
                                            """
                            )
                    )),
            @ApiResponse(responseCode = "500", description = "S3 업로드 또는 DB 저장 실패")
    })
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    PhotoResponse upload(@RequestParam("file") MultipartFile file);

    @Operation(
            summary = "전체 사진 조회 ",
            description = "등록된 모든 사진을 페이지네이션 형태로 조회합니다. page, size, sort 파라미터 사용 가능. 안넣으면 기본값 0, 20"
    )
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호 (0부터 시작)", example = "0"),
            @Parameter(name = "size", description = "페이지 당 항목 수", example = "20"),
            @Parameter(name = "sort", description = "정렬 기준 (필드,정렬방향)", example = "createdAt,desc")
    })
    @ApiResponse(
            responseCode = "200",
            description = "조회 성공",
            content = @Content(
                    schema = @Schema(implementation = PhotoResponse.class),
                    examples = @ExampleObject(
                            name = "사진 목록 조회 예시",
                            value = """
                                    {
                                      "content": [
                                        {
                                          "id": 12,
                                          "imageUrl": "https://s3-bucket/photo12.png"
                                        },
                                        {
                                          "id": 11,
                                          "imageUrl": "https://s3-bucket/photo11.png"
                                        }
                                      ],
                                      "pageable": { "pageNumber": 0, "pageSize": 20 },
                                      "totalPages": 3,
                                      "totalElements": 51,
                                      "last": false
                                    }
                                    """
                    )
            )
    )
    @GetMapping
    Page<PhotoResponse> getAllPhotos(org.springframework.data.domain.Pageable pageable);
}
