package com.induk.python.pythoninweb.controller;

import com.induk.python.pythoninweb.domain.Member;
import com.induk.python.pythoninweb.service.BoardService;
import com.induk.python.pythoninweb.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@AllArgsConstructor
@Controller
public class HomeController {

    private final BoardService boardService;
    private final MemberService memberService;

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        String name = String.valueOf(session.getAttribute("member"));
        Member memberInfo = memberService.memberSelect(name);
        model.addAttribute("memberInfo", memberInfo);
        return "/index";
    }


}
