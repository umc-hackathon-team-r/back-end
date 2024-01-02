package com.example.umchackathonr.domain.customEvent.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


public class CustomEventRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class creatCustomEventDto {
        private String title;
        private LocalDate date;
        private String memo;
        private String target;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class updateCustomEventDto {
        @Schema(description = "이벤트 제목", example = "맹구 생일")
        private String title;

        @Schema(description = "이벤트 날짜", example = "2000-01-10")
        private LocalDate date;

        @Schema(description = "이벤트 메모", example = "저번 생일 못 챙겨줬으니까 비싼거 사주기")
        private String memo;
    }
}
