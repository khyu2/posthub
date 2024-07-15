package study.posthub.domain.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import study.posthub.domain.member.entity.Member;
import study.posthub.domain.member.service.MemberService;
import study.posthub.global.security.LoginUser;

import java.io.IOException;

import static study.posthub.domain.member.entity.Authority.SIGNOUT;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final MemberService memberService;

    @GetMapping("/")
    public String mainPage(Model model, @LoginUser Member member, Long userId) {
//        Member user = memberService.getLoginUser(userId);
        log.info("member: {}", member);

        if (member == null) {
            member = createAnonymousMember();
        }
        model.addAttribute("user", member);
        return "main";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate(); //세션 무효화
        }
        //브라우저 쿠키 안지워지는거 해결해야함.

        //response.sendRedirect("https://nid.naver.com/nidlogin.logout?returl="); //위 코드대신 써도됨 써도 됨.
    }

    private Member createAnonymousMember() {
        Member anonymousMember = new Member();
        anonymousMember.setNickname("익명");
        anonymousMember.setAuthority(SIGNOUT);
        return anonymousMember;
    }
}