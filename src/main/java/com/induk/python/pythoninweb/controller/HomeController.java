package com.induk.python.pythoninweb.controller;

import com.induk.python.pythoninweb.domain.Comment;
import com.induk.python.pythoninweb.domain.Member;
import com.induk.python.pythoninweb.service.CommentService;
import com.induk.python.pythoninweb.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Controller
public class HomeController {

    private final CommentService commentService;
    private final MemberService memberService;

    @GetMapping("/")
    public String home(Model model) {
        List comment = commentService.commentList();
        model.addAttribute("comment", comment);
        return "/index";
    }

    @GetMapping("/view/{id}")
    public String commentView(@PathVariable Long id, Model model) {
        model.addAttribute("comment", commentService.commentDetail(id));
        return "/view";
    }

    @PostMapping("/join")
    public String join(HttpServletRequest request, Model model) {
        Member member = new Member();
        member.setName(request.getParameter("name"));
        member.setLogin_id(request.getParameter("login_id"));
        member.setPw(request.getParameter("pw"));
        String check = memberService.memberJoinCheck(member);
        if (check != null) {
            model.addAttribute("errors", "중복된 아이디가 존재합니다.");
            return "/index";
        }
        memberService.memberJoinInsert(member);
        return "redirect:/";
    }

    @PostMapping("/commentInsert")
    public String contentInsert(HttpServletRequest request) {
        Comment comment = new Comment();
        comment.setTitle(request.getParameter("title"));
        comment.setLogin_id(request.getParameter("loginId"));
        comment.setComment(request.getParameter("content"));
        comment.setName("김");
        commentService.commentInsert(comment);
        return "redirect:/";
    }

    @PostMapping("/commentUpdate/{id}")
    public String commentUpdate(@PathVariable Long id) {

        return "/commentUpdate";
    }

    @PostMapping("commentDelete/{id}")
    public String commentDelete(@PathVariable Long id) {
        commentService.commentDelete(id);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        Member member = new Member();
        String login_id = request.getParameter("id");
        member.setLogin_id(login_id);
        member.setPw(request.getParameter("pw"));
        String check = memberService.memberTrueCheck(member);
        if (check == null) {
            model.addAttribute("errors", "아이디와 비밀번호를 다시 한번 확인해 보세요.");
            return "/index";
        } else {
            HttpSession session = request.getSession(); // 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성하여 반환
            session.setMaxInactiveInterval(300);
            session.setAttribute("member", login_id); // 세션에 로그인 회원 정보 보관
        }
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();   // 세션 날림
        }
        return "redirect:/";
    }

}
