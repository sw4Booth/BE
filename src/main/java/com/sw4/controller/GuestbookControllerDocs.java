package com.sw4.controller;

import com.sw4.dto.GuestbookRequest;
import com.sw4.dto.GuestbookResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "방명록 (Guestbook)", description = "사진에 대한 방명록 작성 및 조회 API")
public interface GuestbookControllerDocs {

    @Operation(summary = "방명록 작성", description = "사진 ID와 메시지를 이용해 방명록을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "방명록 등록 성공",
                    content = @Content(
                            schema = @Schema(implementation = GuestbookResponse.class),
                            examples = @ExampleObject(
                                    name = "방명록 생성 성공 예시",
                                    value = """
                                            {
                                              "id": 1,
                                              "imageUrl": "https://sw4booth.s3.ap-northeast-2.amazonaws.com/uuid_photo.png",
                                              "createdAt": "2025-10-30T10:00:00",
                                              "message": "짱~"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "사진을 찾을 수 없음")
    })
    @RequestBody(required = true, description = "방명록 메시지와 사진 ID")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    GuestbookResponse create(GuestbookRequest req);

    @Operation(summary = "방명록 전체 조회 (페이지네이션)", description = "작성된 모든 방명록을 최신순으로 페이지별 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = Page.class),
                            examples = @ExampleObject(
                                    name = "조회 성공 예시",
                                    value = """
                                            {
                                                  "content": [
                                                      {
                                                          "id": 1,
                                                          "imageUrl": "https://sw4booth.s3.ap-northeast-2.amazonaws.com/d45b8054-f860-4b38-a65f-9eed15fac26f_스크린샷 2025-10-18 174131.png",
                                                          "createdAt": "2025-10-30T06:48:47.99367",
                                                          "message":"짱~"
                                                      }
                                                  ],
                                                  "pageable": {
                                                      "pageNumber": 0,
                                                      "pageSize": 5,
                                                      "sort": {
                                                          "empty": true,
                                                          "sorted": false,
                                                          "unsorted": true
                                                      },
                                                      "offset": 0,
                                                      "paged": true,
                                                      "unpaged": false
                                                  },
                                                  "last": true,
                                                  "totalPages": 1,
                                                  "totalElements": 1,
                                                  "first": true,
                                                  "size": 5,
                                                  "number": 0,
                                                  "sort": {
                                                      "empty": true,
                                                      "sorted": false,
                                                      "unsorted": true
                                                  },
                                                  "numberOfElements": 1,
                                                  "empty": false
                                              }
                                            """
                            )
                    )
            )
    })
    Page<GuestbookResponse> findAll(
            @Parameter(hidden = true) Pageable pageable
    );
}
