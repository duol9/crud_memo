package com.example.Layerd_Acrhitecture.service;


import com.example.Layerd_Acrhitecture.dto.MemoRequestDto;
import com.example.Layerd_Acrhitecture.dto.MemoResponseDto;
import com.example.Layerd_Acrhitecture.entity.Memo;
import com.example.Layerd_Acrhitecture.repository.MemoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Annotation @Service는 @Component와 같다, Spring Bean으로 등록한다는 뜻.
 * Spring Bean으로 등록되면 다른 클래스에서 주입하여 사용할 수 있다.
 * 명시적으로 Service Layer 라는것을 나타낸다.
 * 비지니스 로직을 수행한다.
 */
@Service
public class MemoServiceImpl implements MemoService {

    private final MemoRepository memoRepository;

    public MemoServiceImpl(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    @Override
    public MemoResponseDto saveMemo(MemoRequestDto dto) {

        // 1. 요청받은 데이터로 Memo 객체 생성, ID는 없음
        Memo memo = new Memo(dto.getTitle(), dto.getContents());

        // 2. DB 호출
        Memo savedMemo = memoRepository.saveMemo(memo);
        return new MemoResponseDto(savedMemo);
    }

    @Override
    public ResponseEntity<List<MemoResponseDto>> findAllMemos() {
        // 전체 조회
        ResponseEntity<List<MemoResponseDto>> allMemos = memoRepository.findAllMemos();

        return allMemos;
    }

    @Override
    public MemoResponseDto findMemoById(Long id) {
        //식별자의 Memo가 없다면
        Memo memo = memoRepository.findMemoById(id);

        // 널 방지
        if (memo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dose not exist id = " + id);
        }

        return new MemoResponseDto(memo);
    }

    @Override
    public MemoResponseDto updateMemo(Long id, String title, String contents) {
        Memo memo = memoRepository.findMemoById(id);

        // 널 방지
        if (memo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        //필수값 검증
        if (title == null || contents == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title and content are required values." );
        }

        // 수정
        memo.update(title, contents);

        return new MemoResponseDto(memo);
    }

    @Override
    public MemoResponseDto updateTitle(Long id, String title, String contents) {
        // 메모 조회
        Memo memo = memoRepository.findMemoById(id);

        // 널 방지
        if (memo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        //필수값 검증
        if (title == null || contents != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title and content are required values." );
        }

        // 수정
        memo.updateTitle(title);

        return new MemoResponseDto(memo);
    }

    @Override
    public void deleteMemo(Long id) {
        // 메모 조회
        Memo memo = memoRepository.findMemoById(id);

        // 널 방지
        if (memo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        memoRepository.deleteMemo(id);
    }
}
