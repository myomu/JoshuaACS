package site.joshua.acs.controller;

import site.joshua.acs.domain.Attendance;
import site.joshua.acs.domain.Member;
import site.joshua.acs.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
//@RequestMapping("/main")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping("/")
    public String mainPage() {
        return "redirect:/main";
    }

//    출석체크 폼
    @GetMapping("/attendanceCheck")
    public String checkAttendanceForm() {
        return "/attendance/attendanceCheck";
    }

// redirect를 사용할 땐 경로의 주소값을 넣는 것이 아니라 url 위치를 넣어준다.
//    출석 업로드
    @PostMapping("/attendanceUpload")
    public String uploadAttendance(Attendance attendance) {
        attendanceService.uploadAttendance(attendance);
        return "redirect:/main";
    }

//    출석 수정 폼
    @GetMapping("/attendanceUpdate")
    public String updateAttendanceForm(Model model, Long attendanceId) {
        model.addAttribute("update", attendanceService.getAttendance(attendanceId));
        return "/attendance/attendanceUpdate";
    }

//    출석 수정
    @PostMapping("/attendanceUpdate")
    public String updateAttendance(Attendance attendance) {
        attendanceService.updateAttendance(attendance);
        return "redirect:/main";
    }

//    출석 삭제
    @GetMapping("/attendanceDelete")
    public String deleteAttendance(Long attendanceId) {
        attendanceService.deleteAttendance(attendanceId);
        return "redirect:/main";
    }

//    게시판 상태로 이동
    @GetMapping("/attendanceStatus")
    public String goToAttendanceStatus() {
        return "/attendance/attendanceStatus";
    }

//    회의록으로 이동
    @GetMapping("/minutes")
    public String goToMinutes() {
        return "/minutes/minutes";
    }

//    회원 추가
    @PostMapping("/addMemberPost")
    public String addMember(Member member) {
        attendanceService.addMember(member);
        return "redirect:/";
    }
//    회원 리스트
    @GetMapping("/main")
    public String getMemberList(Model model) {
//        model.addAttribute("getMemberList", attendanceService.getMemberList());
        return "/main";
    }

//    @GetMapping("/main")
//    public String getMemberList(@ModelAttribute Member member, Model model) {
//        model.addAttribute("getMember", attendanceService.getMemberList());
//        return "/main";
//    }

    @ModelAttribute("getMember")
    public List<Member> getMember() {
        return attendanceService.getMemberList();
    }
}
