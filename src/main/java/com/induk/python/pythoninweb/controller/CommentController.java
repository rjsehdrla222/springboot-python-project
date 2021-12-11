package com.induk.python.pythoninweb.controller;

import com.induk.python.pythoninweb.domain.Comment;
import com.induk.python.pythoninweb.service.CommentService;
import com.induk.python.pythoninweb.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;

@AllArgsConstructor
@Controller
public class CommentController {

    private final CommentService commentService;
    private final MemberService memberService;

    @PostMapping("/deleteComment")
    public String commentDelete(HttpServletRequest request) throws Exception {
        Long id = Long.parseLong(request.getParameter("id"));
        String title = request.getParameter("board_id");
        String encodedParam = URLEncoder.encode(title, "UTF-8");
        commentService.commentDelete(id);
        return "redirect:/view/" + encodedParam;
    }

    @PostMapping("/insertComment")
    public String commentInsert(HttpServletRequest request) throws Exception {
        Comment comment = new Comment();
        String t = request.getParameter("login_id");
        String name = memberService.memberNameCheck(t);
        String id = request.getParameter("board_id");
        comment.setComment(request.getParameter("newComment"));
        comment.setLogin_id(t);
        comment.setName(name);
        comment.setBoard_id(id);
        String encodedParam = URLEncoder.encode(id, "UTF-8");
        commentService.commentInsert(comment);
        return "redirect:/view/" + encodedParam;
    }

    /*
    @GetMapping("/commentUpdate")

    public String commentUpdate(HttpServletRequest request) {
        String login_id = request.getParameter("login_id");
        commentService.comment
        return "";
    }
     */
}
