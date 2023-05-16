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
import site.joshua.acs.dto.AttendanceStatusDTO;
import site.joshua.acs.service.AttendanceService;
import site.joshua.acs.service.MemberService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AttendanceController {

    private final MemberService memberService;
    private final AttendanceService attendanceService;

    @GetMapping("/attendances")
    public String attendances(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        model.addAttribute("memberList", new Member());
        return "attendances/attendanceCheck";
    }

    @PostMapping("/attendances/new")
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

    @GetMapping("/attendances/status")
    public String attendanceList(Model model) {
        List<LocalDateTime> attendanceDateList = attendanceService.findNoDuplicateDate();
        List<Attendance> attendances = attendanceService.findAttendances();

        model.addAttribute("attendanceList", attendances);

        // 날짜와 해당 날짜와 일치하는 attendance 를 찾아서 날짜와 개수를 DTO 에 담아서 model 로 html 에 넘겨준다.
        List<AttendanceStatusDTO> attendanceStatusDTOS = new ArrayList<>();
        for (LocalDateTime date : attendanceDateList) {
            int count = 0;
            for (Attendance attendance : attendances) {
                if (attendance.getAttendance_date().equals(date)) {
                    count += 1;
                }
            }
            AttendanceStatusDTO atsDTO = new AttendanceStatusDTO();
            atsDTO.setDatetime(date);
            atsDTO.setCount(count);

            attendanceStatusDTOS.add(atsDTO);
        }
        model.addAttribute("atsDTO", attendanceStatusDTOS);

        return "attendances/attendanceStatus";
    }
}
