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
import java.net.URLEncoder;
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

    @GetMapping("/board1")
    public String freeboard(Model model) {
        int category = 1;
        List notice = boardService.boardNoticeList();
        List free_board = boardService.boardFreeList(category);
        model.addAttribute("free_board", free_board);
        model.addAttribute("notice", notice);
        return "/free-notice-board";
    }

    @GetMapping("/board2")
    public String checkBoard(Model model) {
        int category = 2;
        List notice = boardService.boardNoticeList();
        List free_board = boardService.boardFreeList(category);
        model.addAttribute("free_board", free_board);
        model.addAttribute("notice", notice);
        return "/checks";
    }

    @GetMapping("/board3")
    public String questionsBoard(Model model) {
        int category = 3;
        List notice = boardService.boardNoticeList();
        List free_board = boardService.boardFreeList(category);
        model.addAttribute("free_board", free_board);
        model.addAttribute("notice", notice);
        return "/questions";
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
        board.setCategory(Integer.parseInt(request.getParameter("category")));
        board.setName(name);
        boardService.boardInsert(board);

        return "redirect:/board";
    }

    @GetMapping("/debate")
    public String debate() {
        return "/discussion";
    }

    @GetMapping("/questions")
    public String questions() {
        return "/questions";
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
    public String boardInsert(HttpServletRequest request) throws Exception {
        Board board = new Board();
        String name = memberService.memberNameCheck(request.getParameter("login_id"));
        board.setTitle(request.getParameter("title"));
        board.setLogin_id(request.getParameter("login_id"));
        board.setContents(request.getParameter("contents"));
        String category = request.getParameter("category");
        int integerCategory = Integer.parseInt(category);
        board.setCategory(integerCategory);
        board.setName(name);
        boardService.boardInsert(board);
        String encodedParam = URLEncoder.encode(category, "UTF-8");
        return "redirect:/board" + encodedParam;
    }

    @GetMapping("/boardUpdate/{id}")
    public String boardUpdate(@PathVariable Long id, Model model) {
        model.addAttribute("board", boardService.boardDetail(id));
        return "free-notice-board-update";
    }

    @PostMapping("/boardUpdate")
    public String boardUpdate(HttpServletRequest request) throws Exception {
        Board board = new Board();
        String board_id = request.getParameter("board_id");
        board.setId(Long.parseLong(board_id));
        board.setTitle(request.getParameter("title"));
        board.setLogin_id(request.getParameter("login_id"));
        board.setContents(request.getParameter("contents"));
        board.setCategory(Integer.parseInt(request.getParameter("category")));
        boardService.boardUpdate(board);
        String encodedParam = URLEncoder.encode(board_id, "UTF-8");
        return "redirect:/view/" + encodedParam;
    }

    @PostMapping("boardDelete/{id}")
    public String boardDelete(@PathVariable Long id) {
        boardService.boardDelete(id);
        return "redirect:/board1";
    }

}
