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
        member.createMember(form.getName(), form.getAge(), form.getGender(), form.getGroup()); //이 부분 수정할것. groupId를 넘겨야하는데 group 객체의 형태로 설계되어 있어 고민이 필요하다..
        memberService.join(member);

        return "redirect:/attendanceCheck";
    }

    @ModelAttribute("genderTypes")
    public Gender[] genderTypes() {
        return Gender.values();
    }
}
