package site.joshua.acs.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import site.joshua.acs.domain.Group;
import site.joshua.acs.domain.Member;
import site.joshua.acs.service.GroupService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @GetMapping("/groups/new")
    public String createForm(Model model) {
        model.addAttribute("groupForm", new GroupForm());
        return "/groups/createGroupForm";
    }

    @PostMapping("/groups/new")
    public String create(@Valid GroupForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "groups/createGroupForm";
        }

        Group group = new Group();
        group.createGroup(form.getName());
        groupService.addGroup(group);

        return "redirect:/attendances";
    }

    @GetMapping("/groups")
    public String groups(Model model) {
        List<Group> groupList = groupService.findGroups();
        model.addAttribute("groups", groupList);

        return "groups/groupList";
    }

    @PostMapping("/groups/{groupId}/delete")
    public String deleteGroup(@PathVariable("groupId") Long groupId) {
        System.out.println("정상 작동합니까?");
        System.out.println("groupId = " + groupId);
        groupService.deleteGroup(groupId);
        return "redirect:/groups";
    }

    @GetMapping("/groups/{groupId}/edit")
    public String editMemberForm(Model model, @PathVariable("groupId") Long groupId) {

        Group group = groupService.findOne(groupId);
        GroupForm form = new GroupForm();
        form.setName(group.getGroup_name());

        model.addAttribute("groupForm", form);

        return "groups/editGroupForm";
    }

    @PostMapping("/groups/{groupId}/edit")
    public String editMember(@PathVariable("groupId") Long groupId, @ModelAttribute("groupForm") MemberForm form) {
        groupService.editGroup(groupId, form.getName());

        return "redirect:/groups";
    }

}
