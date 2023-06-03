package site.joshua.acs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import site.joshua.acs.domain.Attendance;
import site.joshua.acs.domain.AttendanceStatus;
import site.joshua.acs.domain.Group;
import site.joshua.acs.domain.Member;
import site.joshua.acs.dto.AttendanceStatusDTO;
import site.joshua.acs.form.AttendanceForm;
import site.joshua.acs.form.MemberForm;
import site.joshua.acs.service.AttendanceService;
import site.joshua.acs.service.GroupService;
import site.joshua.acs.service.MemberService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AttendanceController {

    private final MemberService memberService;
    private final AttendanceService attendanceService;
    private final GroupService groupService;

    @GetMapping("/attendances")
    public String attendances(Model model) {
        List<Member> members = memberService.findMembers();
        List<Group> groups = groupService.findGroups();

        model.addAttribute("members", members);
        model.addAttribute("memberList", new Member());
        model.addAttribute("groups", groups);

        return "attendances/attendanceCheck";
    }

    @PostMapping("/attendances/new")
    public String create(@RequestParam(name = "id", required = false) List<Long> memberIds) {

        if (memberIds == null) {
            log.info("AttendanceNullCheck={}", memberIds);
            return "redirect:/attendances";
        }

        LocalDateTime localDateTime = LocalDateTime.now();

        System.out.println("memberIds = " + memberIds);

        for (Long memberId : memberIds) {
            System.out.println("attendance = " + memberId); //데이터 받아오는 것 체크용.
            Attendance attendance = new Attendance();
            Member member = new Member();
            member.setMemberId(memberId);

            attendance.createAttendance(member, AttendanceStatus.ATTENDANCE, localDateTime);
            attendanceService.addAttendance(attendance);
        }
        return "redirect:/attendances/status";
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

    @GetMapping("/attendances/{dateTime}/edit")
    public String editAttendances(Model model, @PathVariable("dateTime") LocalDateTime dateTime) {

        //출석 체크 체크박스 테이블을 만들기 위한 데이터를 가져온다.
        List<Member> members = memberService.findMembers();
        List<Group> groups = groupService.findGroups();

        //받아온 날짜를 기준으로 Attendance 를 불러옴. 이후 memberId를 List 에 저장해서 AttendanceForm 형태로 저장한다.
        List<Attendance> attendancesByDateTime = attendanceService.findAttendancesByDateTime(dateTime);
        List<Long> memberIds = new ArrayList<>();
        AttendanceForm form = new AttendanceForm();

        for (Attendance attendance : attendancesByDateTime) {
            memberIds.add(attendance.getMember().getId());
        }

        form.setMemberIds(memberIds);

        model.addAttribute("members", members);
        model.addAttribute("groups", groups);
        model.addAttribute("attendanceForm", form);

        return "attendances/editAttendanceForm";
    }

    @PostMapping("/attendances/{dateTime}/edit")
    public String editMember(@PathVariable("dateTime") LocalDateTime dateTime, @RequestParam("memberIds") List<Long> memberIds, @ModelAttribute("groupForm") MemberForm form) {

        //수정 시 아무것도 체크를 하지 않으면 memberIds 가 없기 때문에 에러가 나게 된다. 이것은 어떻게 해결해야할지..??

        //기존의 dateTime 인 Attendance 들을 삭제 (먼저 데이터를 삭제하고 추가해야 한다. 추가하고 삭제하면 체크 된 값이 두번 나타나게 되는 문제가 발생한다.)
        List<Attendance> attendancesByDateTime = attendanceService.findAttendancesByDateTime(dateTime);

        for (Attendance attendance : attendancesByDateTime) {
            Long id = attendance.getId();
            attendanceService.deleteAttendance(id);
        }

        //새로 체크한 것을 기준으로 Attendance 들을 생성
        for (Long memberId : memberIds) {
            Attendance attendance = new Attendance();
            Member member = new Member();
            member.setMemberId(memberId);

            attendance.createAttendance(member, AttendanceStatus.ATTENDANCE, dateTime);
            attendanceService.addAttendance(attendance);
        }

        return "redirect:/attendances/status";
    }

    @PostMapping("/attendances/{dateTime}/delete")
    public String deleteMember(@PathVariable("dateTime") LocalDateTime dateTime) {
        List<Attendance> attendances = attendanceService.findAttendancesByDateTime(dateTime);
        for (Attendance attendance : attendances) {
            attendanceService.deleteAttendance(attendance.getId());
        }
        return "redirect:/attendances/status";
    }

}
