package com.example.memo.controller;

import com.example.memo.dto.MemoRequestDto;
import com.example.memo.dto.MemoResponseDto;
import com.example.memo.entity.Memo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    public ResponseEntity<MemoResponseDto> createMemo(@RequestBody MemoRequestDto dto) {
        // 식별자가 1씩 증가 하도록
        Long memoId = memoList.isEmpty() ? 1 : Collections.max(memoList.keySet()) + 1;  // 저장된 메모가 0개면 id값이 1, 아니면 마지막 id에서 1 증가

        // 요청받은 데이터로 Memo 객체 생성
        Memo memo = new Memo(memoId, dto.getTitle(), dto.getContents());

        // Inmemory DB에 Memo 저장
        memoList.put(memoId, memo);

        // 생성된 데이터가 응답, 응답코드는 201로 설정
        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.CREATED);
    }

    // 메모 목록 조회
    @GetMapping
    public List<MemoResponseDto> findAllMemos() {
        // 리스트 초기화
        List<MemoResponseDto> responseDtoList = new ArrayList<>();

        //HashMap<Memo> => List<MemoResponseDto> 형식으로 변환
        // Map형태로 저장된 Memo List들을 values로 하나하나 가져옴
        for (Memo memo : memoList.values()) {
            MemoResponseDto memoResponseDto = new MemoResponseDto(memo); // 메모 객체를 ResponseeDto 형태로 변환
            responseDtoList.add(memoResponseDto); //ResponseDto 리스트에 저장
        }

        // stream() 형식
        /*responseDtoList = memoList.values().stream()
                .map(MemoResponseDto::new).toList();*/

        return responseDtoList;

    }

    // 메모 조회
    @GetMapping("/{id}")
    public ResponseEntity<MemoResponseDto> findMemoById(@PathVariable Long id) {

        Memo memo = memoList.get(id);
        if (memo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.OK);
    }

    // 메모 수정
    @PutMapping("/{id}")
    public ResponseEntity<MemoResponseDto> updateMemoById(@PathVariable Long id, @RequestBody MemoRequestDto dto) {
        Memo memo = memoList.get(id);

        // 조회할 메모가 없어서 생기는 예외사항 발생 방지
        if (memo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // 필수값 검증
        // 내용 또는 제목이 들어있지 않을경우
        if(dto.getTitle() == null || dto.getContents() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        memo.update(dto);

        // 응답
        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.OK);
    }

    // 메모 제목 수정
    @PatchMapping("/{id}")
    public ResponseEntity<MemoResponseDto> updateTitle(@PathVariable Long id, @RequestBody MemoRequestDto dto) {
        Memo memo = memoList.get(id);

        // 없을 경우 방지
        if (memo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (dto.getTitle() == null || dto.getContents() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        memo.updateTitle(dto);

        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.OK);

    }

    //메모 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemo(@PathVariable Long id) {

        // MemoList에 Key값에 id를 포함하고 있다면
        if (memoList.containsKey(id)) {
            memoList.remove(id);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        // 포함하고 있지 않다면
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
