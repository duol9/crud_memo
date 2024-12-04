package com.example.memo.entity;

import com.example.memo.dto.MemoRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

// 메모 클래스
@Getter
@AllArgsConstructor
public class Memo {

    // 속성
    private Long id; //서버에서 관리
    private String title; // 클라이언트에게서
    private String Contents; // 받아옴

    public void update(MemoRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.Contents = requestDto.getContents();
    }

    public void updateTitle(MemoRequestDto dto) {
        this.title = dto.getTitle();
    }

}
