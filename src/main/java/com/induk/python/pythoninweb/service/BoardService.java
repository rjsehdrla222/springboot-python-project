package com.induk.python.pythoninweb.service;

import com.induk.python.pythoninweb.domain.Board;
import com.induk.python.pythoninweb.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public List<Board> boardNoticeList() {
        return boardRepository.boardNoticeList();
    }

    public List<Board> boardFreeList(int category) {
        return boardRepository.boardFreeList(category);
    }

    public int boardCount(int category) {
        return boardRepository.boardCount(category);
    }

    public List boardListList(Map paging) {
        return boardRepository.boardListList(paging);
    }

    public void boardInsert(Board board) {
        boardRepository.boardInsert(board);
    }

    public List<Board> boardDetail(Long id) {
        boardRepository.viewCnt(id);
        return boardRepository.boardDetail(id);
    }

    public void boardDelete(Long id) {
        boardRepository.boardDelete(id);
    }

    public void boardUpdate(Board board) {
        boardRepository.boardUpdate(board);
    }
}
