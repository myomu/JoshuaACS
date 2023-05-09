package site.joshua.acs.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import site.joshua.acs.domain.Gender;
import site.joshua.acs.domain.Group;
import site.joshua.acs.domain.Member;
import site.joshua.acs.service.GroupService;
import site.joshua.acs.service.MemberService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final GroupService groupService;

    @GetMapping("/members")
    public String MemberList(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

    @GetMapping("/members/new")
    public String createForm(Model model) {

        List<Group> groups = groupService.findGroups();

        model.addAttribute("memberForm", new MemberForm());
        model.addAttribute("groups",groups);
        return "/members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, @RequestParam("groupId") Long groupId, BindingResult result) {

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Member member = new Member();
        Group group = new Group();
        group.setGroupId(groupId);
        //group 객체에 id를 저장할 수 있는 메서드를 만들어두고 파라미터로 받아온 groupId를 저장한 뒤 group 객체를 createMember 로 넘겨준다.
        member.createMember(form.getName(), form.getAge(), form.getGender(), group);

        memberService.join(member);

        return "redirect:/attendanceCheck";
    }

    @ModelAttribute("genderTypes")
    public Gender[] genderTypes() {
        return Gender.values();
    }
}
