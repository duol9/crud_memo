package com.example.memo.dto;

import com.example.memo.entity.Memo;
import lombok.Getter;

// 응답 데이터 처리
@Getter
public class MemoResponseDto {

    private Long id;
    private String title;
    private String contents;

    // 생성자
    //Response형태로 바뀌어서 응답 되어야 함
    public MemoResponseDto(Memo memo) {
        this.id = memo.getId();
        this.title = memo.getTitle();
        this.contents = memo.getContents();
    }
}
