package site.joshua.acs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import site.joshua.acs.domain.Attendance;
import site.joshua.acs.domain.AttendanceStatus;
import site.joshua.acs.domain.Member;
import site.joshua.acs.service.AttendanceService;
import site.joshua.acs.service.MemberService;

import java.time.LocalDateTime;
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

    @PostMapping("/attendanceCheck/new")
    public String create(@RequestParam("id") List<Long> memberIds) {

        LocalDateTime localDateTime = LocalDateTime.now();

        for (Long memberId : memberIds) {
            System.out.println("attendance = " + memberId); //데이터 받아오는 것 체크용.
            Attendance attendance = new Attendance();
            Member member = new Member();
            member.setMemberId(memberId);

            attendance.createAttendance(member, AttendanceStatus.ATTENDANCE, localDateTime);
            attendanceService.addAttendance(attendance);
        }
        return "redirect:/";
    }
}
