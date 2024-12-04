package com.example.Layerd_Acrhitecture.service;

import com.example.Layerd_Acrhitecture.dto.MemoRequestDto;
import com.example.Layerd_Acrhitecture.dto.MemoResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MemoService {
    MemoResponseDto saveMemo(MemoRequestDto dto);
    ResponseEntity<List<MemoResponseDto>> findAllMemos();
    MemoResponseDto findMemoById(Long id);
    MemoResponseDto updateMemo(Long id, String title, String contents);
    MemoResponseDto updateTitle(Long id, String title, String contents);
    void deleteMemo(Long id);
}
