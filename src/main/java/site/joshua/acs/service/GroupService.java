package site.joshua.acs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.joshua.acs.domain.Gender;
import site.joshua.acs.domain.Group;
import site.joshua.acs.domain.Member;
import site.joshua.acs.repository.GroupRepository;
import site.joshua.acs.repository.MemberRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final MemberService memberService;

    @Transactional
    public Long addGroup(Group group) {
        groupRepository.save(group);
        return group.getId();
    }

    public List<Group> findGroups() {
        return groupRepository.findAll();
    }

    public Group findOne(Long groupId) {
        return groupRepository.findOne(groupId);
    }

    @Transactional
    public Long deleteGroup(Long groupId) {
//        List<Member> members = memberService.findMembersByGroupId(groupId);
//        for (Member member : members) {
//            memberService.deleteMember(member);
//        }
        Group group = groupRepository.findOne(groupId);
        groupRepository.delete(group);
        return groupId;
    }

    @Transactional
    public void editGroup(Long groupId, String name) {
        Group group = groupRepository.findOne(groupId);
        group.editGroup(name);
    }

}
