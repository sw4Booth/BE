package com.sw4.controller;

import com.sw4.dto.ShareLinkRequest;
import com.sw4.dto.ShareLinkResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "공유 링크 (ShareLink)", description = "사진 공유 URL 및 QR 코드 생성/조회 API")
public interface ShareLinkControllerDocs {

    @Operation(summary = "공유 링크 생성", description = "Photo ID를 기반으로 고유 링크와 QR 코드를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "링크 생성 성공",
                    content = @Content(
                            schema = @Schema(implementation = ShareLinkResponse.class),
                            examples = @ExampleObject(
                                    name = "링크 생성 성공 예시",
                                    value = """
                                            {
                                              "id": 1,
                                              "uuid": "a1b2c3d4-e5f6-7g8h-9i0j-k1l2m3n4o5p6",
                                              "imageUrl": "https://sw4booth.s3.ap-northeast-2.amazonaws.com/uuid_photo.png",
                                              "qrImageBase64": "data:image/png;base64,iVBORw0KGgoAAA..."
                                            }
                                            """
                            )
                    )),
            @ApiResponse(responseCode = "404", description = "사진을 찾을 수 없음")
    })
    ShareLinkResponse create(ShareLinkRequest req);

    @Operation(summary = "공유 링크 조회", description = "UUID를 통해 원본 사진과 QR 코드 Base64 데이터를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "링크 조회 성공",
                    content = @Content(schema = @Schema(implementation = ShareLinkResponse.class))),
            @ApiResponse(responseCode = "404", description = "공유 링크를 찾을 수 없음")
    })
    ShareLinkResponse get(@PathVariable String uuid);
}
