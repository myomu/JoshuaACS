package site.joshua.acs.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import site.joshua.acs.argumentresolver.Login;
import site.joshua.acs.domain.User;

@Controller
@Slf4j
public class HomeController {

    @RequestMapping("/")
    public String homeLogin(@Login User loginUser, Model model) {

        //세션에 사용자가 없으면 로그인 form 으로 보낸다.
        if (loginUser == null) {
            return "redirect:/login";
        }

        //세션이 유지되면 home 화면으로 이동한다.
        model.addAttribute("user", loginUser);
        return "home";
    }
}
