package com.example.Layerd_Acrhitecture.controller;

import com.example.Layerd_Acrhitecture.dto.MemoRequestDto;
import com.example.Layerd_Acrhitecture.dto.MemoResponseDto;
import com.example.Layerd_Acrhitecture.service.MemoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // @Controller + @ResponseBody
@RequestMapping("/memos") // Prefix
public class MemoController {

    /*// 데베 (Repository가 할 일)
    private final Map<Long, Memo> memoList = new HashMap<>();*/

    // 주입된 의존성을 변경할 수 없어 객체의 상태를 안전하게 유지할 수 있다.
    // final필수
    private final MemoService memoService;

    /**
     * 생성자 주입
     * 클래스가 필요로 하는 의존성을 생성자를 통해 전달하는 방식
     * @param memoService @Service로 등록된 MemoService 구현체인 Impl
     */
    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    /*// 1. 요청
    @PostMapping
    public ResponseEntity<MemoResponseDto> createMemo(@RequestBody MemoRequestDto dto) {

        //2. 비즈니스 로직
        // 식별자가 1씩 증가 하도록 만듦 (Respository 층에서 해야할 일)
        Long memoId = memoList.isEmpty() ? 1 : Collections.max(memoList.keySet()) + 1;

        // 요청받은 데이터로 Memo 객체 생성 (Service 층에서 해야할 일)
        Memo memo = new Memo(memoId, dto.getTitle(), dto.getContents());

        // 3. 디비 상호작용
        // Inmemory DB에 Memo 메모 (Repository가 해야할 일)
        memoList.put(memoId, memo);

        // 4. 응답
        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.CREATED);
    }*/

    /**
     * 메모 생성 API
     * @param : {@link MemoRequestDto} 메모 생성 요청 객체
     * @return : {@link ResponseEntity<MemoResponseDto>} JSON 응답
     */
    @PostMapping // 요청
    public ResponseEntity<MemoResponseDto> createMemo(@RequestBody MemoRequestDto requestDto) {
        // ServiceLayer 호출 및 응답
        return new ResponseEntity<>(memoService.saveMemo(requestDto), HttpStatus.CREATED);
    }

    /**
     * 메모 전체 조회 API
     * 이 메서드를 호출해서 얻은 반환값을 ResponseEntity<MemoResponseDto>로 만들어줌
     * @return : {@link ResponseEntity<MemoResponseDto>} JSON 응답
     */
    @GetMapping
    public ResponseEntity<List<MemoResponseDto>> findAllMemos() {
        return memoService.findAllMemos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemoResponseDto> findMemoById(@PathVariable Long id) {
        return new ResponseEntity<>(memoService.findMemoById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemoResponseDto> updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto dto) {
        return new ResponseEntity<>(memoService.updateMemo(id, dto.getTitle(), dto.getContents()), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MemoResponseDto> updateTitle(@PathVariable Long id, @RequestBody MemoRequestDto dto) {
        return new ResponseEntity<>(memoService.updateTitle(id, dto.getTitle(), dto.getContents()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemo(@PathVariable Long id) {
        memoService.deleteMemo(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
