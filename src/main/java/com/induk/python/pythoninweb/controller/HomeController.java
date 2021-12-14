package com.induk.python.pythoninweb.controller;

import com.induk.python.pythoninweb.domain.Member;
import com.induk.python.pythoninweb.service.ApisApiService;
import com.induk.python.pythoninweb.service.BoardService;
import com.induk.python.pythoninweb.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Slf4j
@AllArgsConstructor
@Controller
public class HomeController {

    private final MemberService memberService;
    private final ApisApiService apisApiService;
    private final BoardService boardService;

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        log.info("접속 로그");
        String name = String.valueOf(session.getAttribute("member"));
        Member memberInfo = memberService.memberSelect(name);
        model.addAttribute("board1", boardService.boardFreeList(1));
        model.addAttribute("board2", boardService.boardFreeList(2));
        model.addAttribute("borrowValue", apisApiService.apiSelectValue());
        model.addAttribute("memberInfo", memberInfo);
        return "/index";
    }

}
