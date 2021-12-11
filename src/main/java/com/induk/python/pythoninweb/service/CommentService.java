package com.induk.python.pythoninweb.service;

import com.induk.python.pythoninweb.domain.Comment;
import com.induk.python.pythoninweb.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> commentList(Long board_id) {
        return commentRepository.commentList(board_id);
    }

    public void commentDelete(Long id) {
        commentRepository.commentDelete(id);
    }

    public void commentInsert(Comment comment) {
        commentRepository.commentInsert(comment);
    }

}
