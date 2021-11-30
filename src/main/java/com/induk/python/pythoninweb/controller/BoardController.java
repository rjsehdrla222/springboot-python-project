package com.induk.python.pythoninweb.controller;

import com.induk.python.pythoninweb.domain.Board;
import com.induk.python.pythoninweb.domain.Member;
import com.induk.python.pythoninweb.service.BoardService;
import com.induk.python.pythoninweb.service.CommentService;
import com.induk.python.pythoninweb.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@AllArgsConstructor
public class BoardController {

    private final MemberService memberService;
    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/notice")
    public String notice() {
        return "/notice";
    }

    @GetMapping("/freeboard")
    public String freeboard(Model model) {
        List board = boardService.boardList();
        model.addAttribute("board", board);
        return "free-notice-board";
    }

    @GetMapping("/free-notice-board-write")
    public String freeboardWrite(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        model.addAttribute("id", id);
        return "free-notice-board-write";
    }

    @PostMapping("/free-notice-board-write")
    public String freeboardWrite(HttpServletRequest request) {
        Board board = new Board();

        String login_id = request.getParameter("login_id");
        String name = memberService.memberNameCheck(login_id);

        board.setTitle(request.getParameter("title"));
        board.setLogin_id(login_id);
        board.setContents(request.getParameter("contents"));
        board.setName(name);
        boardService.boardInsert(board);
        return "redirect:/freeboard";
    }

    @GetMapping("/debate")
    public String debate() {
        return "/discussion";
    }

    @GetMapping("/questions")
    public String questions() {
        return "/question";
    }

    @GetMapping("/br1")
    public String br1() {
        return "/br1";
    }

    @GetMapping("/view/{id}")
    public String boardView(@PathVariable Long id, Model model) {
        model.addAttribute("board", boardService.boardDetail(id));
        model.addAttribute("comment", commentService.commentList(id));
        return "/view";
    }

    @PostMapping("/boardInsert")
    public String boardInsert(HttpServletRequest request) {
        Board board = new Board();
        String name = memberService.memberNameCheck(request.getParameter("loginId"));
        board.setTitle(request.getParameter("title"));
        board.setLogin_id(request.getParameter("loginId"));
        board.setContents(request.getParameter("content"));
        board.setName(name);
        boardService.boardInsert(board);
        return "redirect:/";
    }

    @GetMapping("/boardUpdate/{id}")
    public String boardUpdate(@PathVariable Long id, Model model) {
        model.addAttribute("board", boardService.boardDetail(id));
        return "free-notice-board-update";
    }

    @PostMapping("/boardUpdate")
    public String boardUpdate(HttpServletRequest request) {
        Board board = new Board();
        board.setId(Long.parseLong(request.getParameter("board_id")));
        board.setTitle(request.getParameter("title"));
        board.setLogin_id(request.getParameter("login_id"));
        board.setContents(request.getParameter("contents"));
        boardService.boardUpdate(board);
        return "redirect:/freeboard";
    }

    @PostMapping("boardDelete/{id}")
    public String boardDelete(@PathVariable Long id) {
        boardService.boardDelete(id);
        return "redirect:/freeboard";
    }

}
