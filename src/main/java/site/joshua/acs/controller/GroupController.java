package site.joshua.acs.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import site.joshua.acs.domain.Group;
import site.joshua.acs.domain.Member;
import site.joshua.acs.service.GroupService;

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

}
