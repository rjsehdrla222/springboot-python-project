package com.induk.python.pythoninweb.repository;

import com.induk.python.pythoninweb.domain.Board;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository {

    List<Board> boardList();
    void boardInsert(Board board);
    List<Board> boardDetail(Long id);
    void viewCnt(Long id);
    void boardDelete(Long id);
    void boardUpdate(Board board);

}