package com.induk.python.pythoninweb.controller;

import com.induk.python.pythoninweb.domain.Board;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping(value = {"/board{i}", "/board{i}/page={num}"})
    public String freeboard(Model model, @PathVariable(required = false) Integer num, @PathVariable int i) {
        if (num == null) {
            num = 0;
        } else {
            num = (num - 1) * 5;
        }
        int category = i;
        /**
         * 페이징 위해 원하는 페이지와 카테고리를 맵에 넣어서 전달하고 값을 받아옴.
         * 이 결과는 원하는 페이지에 작성되었을 내용들을 가져오는 작업.
         */
        Map<String, Integer> map = new HashMap<>();
        map.put("num", num);
        map.put("category", category);
        List paging = boardService.boardListList(map);

        /**
         * 페이지의 숫자를 총 몇개 만들어낼것인지에 대한 작업
         */
        int total = boardService.boardCount(category);
        int count = (total / 5) + 1; // 카테고리에 맞는 등록된 글의 총합을 토대로 몇 페이지가 존재하는지.

        List notice = boardService.boardNoticeList();

        if (i == 1) {
            model.addAttribute("pageCount", count);
            model.addAttribute("free_board", paging);
            model.addAttribute("notice", notice);
            return "/free-notice-board";
        } else if (i == 2) {
            model.addAttribute("free_board", paging);
            model.addAttribute("notice", notice);
            return "/checks";
        } else if (i == 3) {
            model.addAttribute("free_board", paging);
            model.addAttribute("notice", notice);
            return "/questions";
        } else {
            model.addAttribute("errors", "잘못된 접근입니다.");
            return "/errors";
        }
    }

    @GetMapping("/free-notice-board-write")
    public String freeboardWrite(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        model.addAttribute("id", id);
        return "free-notice-board-write";
    }

    @PostMapping("/free-notice-board-write")
    public String freeboardWriteInsert(HttpServletRequest request, Model model) {
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
