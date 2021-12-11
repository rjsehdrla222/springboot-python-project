package com.induk.python.pythoninweb.repository;

import com.induk.python.pythoninweb.domain.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository {

    List<Comment> commentList(Long board_id);
    void commentDelete(Long id);
    void commentInsert(Comment comment);
}
