package site.joshua.acs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.joshua.acs.domain.Gender;
import site.joshua.acs.domain.Group;
import site.joshua.acs.domain.Member;
import site.joshua.acs.repository.MemberRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 추가
     */
    @Transactional
    public Long join(Member member) {
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 모든 회원을 찾는다.
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * memberId 에 해당하는 회원을 찾는다.
     */
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    /**
     * groupId 에 해당하는 회원들을 찾아 List 로 반환한다.
     */
    public List<Member> findMembersByGroupId(Long groupId) {
        return memberRepository.findAllByGroupId(groupId);
    }

    /**
     * memberId 에 해당하는 회원의 정보를 수정한다.
     */
    @Transactional //변경 감지 방식
    public void editMember(Long memberId, String name, int age, Gender gender, Group group) {
        Member member = memberRepository.findOne(memberId);
        member.editMember(name, age, gender, group);
    }

    /**
     * memberId 에 해당하는 회원을 삭제한다.
     */
    @Transactional
    public Long deleteMember(Long memberId) {
        Member member = memberRepository.findOne(memberId);
        memberRepository.delete(member);
        return member.getId();
    }

}
