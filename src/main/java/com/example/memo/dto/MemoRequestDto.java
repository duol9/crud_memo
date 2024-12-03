package com.example.memo.dto;

import lombok.Getter;

// 요청 데이터 처리
@Getter
public class MemoRequestDto {

    private String title;
    private String contents;
}
