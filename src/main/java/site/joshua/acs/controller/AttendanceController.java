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
import site.joshua.acs.service.AttendanceService;
import site.joshua.acs.service.GroupService;
import site.joshua.acs.service.MemberService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AttendanceController {

    private final MemberService memberService;
    private final AttendanceService attendanceService;
    private final GroupService groupService;

    /**
     * 출석 체크 화면 GET 요청
     */
    @GetMapping("/attendances")
    public String attendances(Model model) {
        List<Member> members = memberService.findMembers();
        List<Group> groups = groupService.findGroups();

        model.addAttribute("members", members);
        model.addAttribute("memberList", new Member());
        model.addAttribute("groups", groups);

        return "attendances/attendanceCheck";
    }

    /**
     * 출석 체크 생성
     */
    @PostMapping("/attendances/new")
    public String create(@RequestParam(name = "id", required = false) List<Long> memberIds,
                         @RequestParam(name = "year", required = false) String year,
                         @RequestParam(name = "month", required = false) String month,
                         @RequestParam(name = "day", required = false) String day) {

        // 아무것도 체크가 되지 않으면 memberIds List 인스턴스가 생성되지 않으므로 isEmpty()는 사용 불가능하다. null로 체크하자.
        if (memberIds.isEmpty()) {
            return "redirect:/attendances";
        }

        // LocalDateTime 변수 생성.
        LocalDateTime dateTime;

        if (year == null || month == null || day == null) {
            dateTime = LocalDateTime.now();
        } else {
            String date = year+"-"+month+"-"+day+" 00:00:00";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            dateTime = LocalDateTime.parse(date, formatter);
        }

        for (Long memberId : memberIds) {
            Attendance attendance = new Attendance();
            Member member = new Member();
            member.setMemberId(memberId);

            attendance.createAttendance(member, AttendanceStatus.ATTENDANCE, dateTime);
            attendanceService.addAttendance(attendance);
        }

        return "redirect:/attendances/status";
    }

    /**
     * 출석 목록 화면 GET 요청
     */
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

    /**
     * 출석 체크 수정 화면 GET 요청
     */
    @GetMapping("/attendances/{dateTime}/edit")
    public String editAttendances(Model model, @PathVariable("dateTime") LocalDateTime dateTime) {

        // 출석 체크 체크박스 테이블을 만들기 위한 데이터를 가져온다.
        List<Member> members = memberService.findMembers();
        List<Group> groups = groupService.findGroups();

        // 받아온 날짜를 기준으로 Attendance 를 불러옴. 이후 memberId를 List 에 저장해서 AttendanceForm 형태로 저장한다.
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

    /**
     * 출석 체크 수정
     */
    @PostMapping("/attendances/{dateTime}/edit")
    public String editMember(@PathVariable("dateTime") LocalDateTime dateTime,
                             @RequestParam(name = "memberIds", required = false) List<Long> memberIds,
                             @RequestParam(name = "year", required = false) String year,
                             @RequestParam(name = "month", required = false) String month,
                             @RequestParam(name = "day", required = false) String day) {

        // 수정 시 아무것도 체크를 하지 않으면 수정 화면으로 다시 redirect.
        if (memberIds == null) {
            return "redirect:/attendances/{dateTime}/edit";
        }

        // 기존 LocalDateTime 을 저장. 만약 시간을 다시 설정하면 dateTime 이 변경됨으로 아래에서 기존의 출석들을 삭제할 때 사용.
        LocalDateTime preDateTime = dateTime;

        if (year != null && month != null && day != null) {
            String date = year+"-"+month+"-"+day+" 00:00:00";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            dateTime = LocalDateTime.parse(date, formatter);
        }

        // 기존의 dateTime 인 Attendance 들을 삭제 (먼저 데이터를 삭제하고 추가해야 한다. 추가하고 삭제하면 체크 된 값이 두번 나타나게 되는 문제가 발생한다.)
        List<Attendance> attendancesByDateTime = attendanceService.findAttendancesByDateTime(preDateTime);

        for (Attendance attendance : attendancesByDateTime) {
            Long id = attendance.getId();
            attendanceService.deleteAttendance(id);
        }

        // 새로 체크한 것을 기준으로 Attendance 들을 생성
        for (Long memberId : memberIds) {
            Attendance attendance = new Attendance();
            Member member = new Member();
            member.setMemberId(memberId);

            attendance.createAttendance(member, AttendanceStatus.ATTENDANCE, dateTime);
            attendanceService.addAttendance(attendance);
        }

        return "redirect:/attendances/status";
    }

    /**
     * 출석 체크 삭제
     */
    @PostMapping("/attendances/{dateTime}/delete")
    public String deleteMember(@PathVariable("dateTime") LocalDateTime dateTime) {
        List<Attendance> attendances = attendanceService.findAttendancesByDateTime(dateTime);
        for (Attendance attendance : attendances) {
            attendanceService.deleteAttendance(attendance.getId());
        }

        return "redirect:/attendances/status";
    }

}
