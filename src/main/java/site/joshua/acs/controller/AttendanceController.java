package site.joshua.acs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import site.joshua.acs.domain.Attendance;
import site.joshua.acs.domain.Member;
import site.joshua.acs.service.AttendanceService;
import site.joshua.acs.service.MemberService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AttendanceController {

    private final MemberService memberService;
    private final AttendanceService attendanceService;

    @GetMapping("/attendanceCheck")
    public String attendances(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        model.addAttribute("memberList", new Member());
        return "attendance/attendanceCheck";
    }

    //출석체크 DB로 보내는 과정 해결 안됨.
    @PostMapping("/attendanceCheck/new")
    public String create(@RequestParam("memI") List<String> attendances) {

        for (String attendance : attendances) {
            System.out.println("attendance = " + attendance);
            //attendanceService.addAttendance(attendance);
        }
        return "redirect:/";
    }
}
