package com.example.Layerd_Acrhitecture.repository;

import com.example.Layerd_Acrhitecture.dto.MemoResponseDto;
import com.example.Layerd_Acrhitecture.entity.Memo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MemoRepository {

    //id가 없는 채로 전달 될 예정
    Memo saveMemo(Memo memo);
    ResponseEntity<List<MemoResponseDto>> findAllMemos();
    Memo findMemoById(Long id);
    void deleteMemo(Long id);
}
