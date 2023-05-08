package site.joshua.acs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.joshua.acs.domain.Group;
import site.joshua.acs.repository.GroupRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

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
}
