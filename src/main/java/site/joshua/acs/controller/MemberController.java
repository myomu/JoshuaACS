package site.joshua.acs.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import site.joshua.acs.domain.Attendance;
import site.joshua.acs.domain.Gender;
import site.joshua.acs.domain.Group;
import site.joshua.acs.domain.Member;
import site.joshua.acs.dto.MemberListDTO;
import site.joshua.acs.form.MemberForm;
import site.joshua.acs.service.AttendanceService;
import site.joshua.acs.service.GroupService;
import site.joshua.acs.service.MemberService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final GroupService groupService;
    private final AttendanceService attendanceService;

    /**
     * 회원 목록 화면 GET 요청
     */
    @GetMapping("/members")
    public String MemberList(Model model) {
        List<Member> members = memberService.findMembers();
        List<Attendance> attendances = attendanceService.findAttendances();
        List<LocalDateTime> attendanceDateTime = attendanceService.findNoDuplicateDate();
        List<MemberListDTO> memberListDTOS = new ArrayList<>();

        for (Member member : members) {
            int count = 0;
            for (Attendance attendance : attendances) {
                if (member == attendance.getMember()) {
                    count++;
                }
            }
            // 출석률을 계산하여 member 객체와 함께 MemberListDTO 에 저장시킨다.
            // 출석 날짜를 기준으로 나누기 때문에 findNoDuplicateDate()로 중복없는 날짜를 가져와 나눠준다.
            Double rate = Math.round((double) count / attendanceDateTime.size() * 10000) / 100.0;
            memberListDTOS.add(new MemberListDTO(member, rate, count));
        }
        model.addAttribute("memberListDTO", memberListDTOS);

        return "members/memberList";
    }

    /**
     * 회원 추가 화면 GET 요청
     */
    @GetMapping("/members/new")
    public String createForm(Model model) {

        List<Group> groups = groupService.findGroups();

        model.addAttribute("memberForm", new MemberForm());
        model.addAttribute("groups",groups);

        return "members/createMemberForm";
    }

    /**
     * 회원 추가
     */
    @PostMapping("/members/new")
    public String createMember(@Valid MemberForm form, @RequestParam("groupId") Long groupId, BindingResult result) {

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Member member = new Member();
        Group group = new Group();
        group.setGroupId(groupId);

        // group 객체에 id를 저장할 수 있는 메서드를 만들어두고 파라미터로 받아온 groupId를 저장한 뒤 group 객체를 createMember 로 넘겨준다.
        member.createMember(form.getName(), form.getAge(), form.getGender(), group);
        memberService.join(member);

        return "redirect:/members";
    }

    /**
     * 회원 수정 화면 GET 요청
     */
    @GetMapping("/members/{memberId}/edit")
    public String editMemberForm(Model model, @PathVariable("memberId") Long memberId) {

        Member member = memberService.findOne(memberId);
        List<Group> groups = groupService.findGroups();

        MemberForm form = new MemberForm();
        form.setName(member.getName());
        form.setAge(member.getAge());
        form.setGender(member.getGender());
        form.setGroup(member.getGroup());

        model.addAttribute("memberForm", form);
        model.addAttribute("groups", groups);

        return "members/editMemberForm";
    }

    /**
     * 회원 수정
     */
    @PostMapping("/members/{memberId}/edit")
    public String editMember(@PathVariable("memberId") Long memberId, @RequestParam("groupId") Long groupId, @ModelAttribute("memberForm") MemberForm form) {

        Group group = new Group();
        group.setGroupId(groupId);

        memberService.editMember(memberId, form.getName(), form.getAge(), form.getGender(), group);

        return "redirect:/members";
    }

    /**
     * 회원 삭제
     */
    @PostMapping("/members/{memberId}/delete")
    public String deleteMember(@PathVariable("memberId") Long memberId) {
        memberService.deleteMember(memberId);

        return "redirect:/members";
    }

    /**
     * View 단으로 Gender 의 description 값을 넘겨준다.
     */
    @ModelAttribute("genderTypes")
    public Gender[] genderTypes() {
        return Gender.values();
    }

}

