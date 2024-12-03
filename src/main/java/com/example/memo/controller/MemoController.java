package com.example.memo.controller;

import com.example.memo.dto.MemoRequestDto;
import com.example.memo.dto.MemoResponseDto;
import com.example.memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// Memo 생성 API
@RestController
// prifix하는 URL을 설정할 때 사용
@RequestMapping("/memos") // 공통으로 들어가지는 url 작성
public class MemoController {

    // Map 자료구조가 DB 역할
    private final Map<Long, Memo> memoList = new HashMap<>();

    // 메모 생성
    // 생성해야 되니까 PostMapping 어노테이션 사용
    @PostMapping
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto dto) {
        // 식별자가 1씩 증가 하도록
        Long memoId = memoList.isEmpty() ? 1 : Collections.max(memoList.keySet()) + 1;  // 저장된 메모가 0개면 id값이 1, 아니면 마지막 id에서 1 증가

        // 요청받은 데이터로 Memo 객체 생성
        Memo memo = new Memo(memoId, dto.getTitle(), dto.getContents());

        // Inmemory DB에 Memo 저장
        memoList.put(memoId, memo);

        return new MemoResponseDto(memo);
    }


    // 메모 조회
    @GetMapping("/{id}")
    public MemoResponseDto findMemoById(@PathVariable Long id) {

        Memo memo = memoList.get(id);

        return new MemoResponseDto(memo);
    }

    // 메모 수정
    @PutMapping("/{id}")
    public MemoResponseDto updateMemoById(@PathVariable Long id, @RequestBody MemoRequestDto dto) {
        Memo memo = memoList.get(id);

        memo.update(dto);

        return new MemoResponseDto(memo);
    }

    //메모 삭제
    @DeleteMapping("/{id}")
    public void deleteMemo(@PathVariable Long id) {
        memoList.remove(id);
    }
}
