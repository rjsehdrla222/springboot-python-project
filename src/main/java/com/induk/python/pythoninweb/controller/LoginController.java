package com.induk.python.pythoninweb.controller;

import com.induk.python.pythoninweb.domain.Member;
import com.induk.python.pythoninweb.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@AllArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String join() {
        return "/sign-up";
    }

    @PostMapping("/join")
    public String join(HttpServletRequest request, Model model) {
        Member member = new Member();
        String name = request.getParameter("name");
        log.info(name + "이 회원가입.");
        member.setName(name);
        member.setLogin_id(request.getParameter("login_id"));
        member.setPw(request.getParameter("pw"));
        String check = memberService.memberJoinCheck(member);
        if (check != null) {
            model.addAttribute("errors", "중복된 아이디가 존재합니다.");
            return "/errors";
        }
        memberService.memberJoinInsert(member);
        return "redirect:/";
    }

    @PostMapping("/memberUpdate")
    public String memberUpdate(HttpServletRequest request) {
        String login_id = request.getParameter("login_id");
        System.out.println(login_id + " 을 이용해서 이 이름에 해당하는 컬럼을 가져와서 보여주고 " +
                "업데이트 할 수 있도록 만들면 된다.");
        return "memberUpdate";
    }

    @GetMapping("/login")
    public String login() {
        return "/sign-in";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        Member member = new Member();
        String login_id = request.getParameter("id");
        log.info(login_id + "이 로그인.");
        member.setLogin_id(login_id);
        member.setPw(request.getParameter("pw"));
        String check = memberService.memberTrueCheck(member);
        if (check == null) {
            model.addAttribute("errors", "아이디와 비밀번호를 다시 한번 확인해 보세요.");
            return "/errors";
        } else {
            HttpSession session = request.getSession(); // 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성하여 반환
            session.setMaxInactiveInterval(300);
            session.setAttribute("member", login_id); // 세션에 로그인 회원 정보 보관
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object login_id = session.getAttribute("member");
        log.info(login_id + "이 로그아웃.");
        if (session != null) {
            session.invalidate();   // 세션 날림
        }
        return "redirect:/";
    }
}
