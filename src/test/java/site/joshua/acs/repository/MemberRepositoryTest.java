package site.joshua.acs.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import site.joshua.acs.domain.Gender;
import site.joshua.acs.domain.Group;
import site.joshua.acs.domain.Member;
import site.joshua.acs.service.MemberService;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class MemberRepositoryTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("동일한 멤버인지 체크")
    @Transactional
    void testMember() {
        //given
        Member member = new Member();
        Group group = new Group();

        member.createMember("memberA", 20, Gender.MAN, null);

        //when
        //memberRepository.save(member);
        Long saveId = memberService.join(member);

        //then
        assertThat(member.getId()).isEqualTo(1L);
        System.out.println("memberID" + member.getId());
        System.out.println("memberName = " + member.getName());
        System.out.println("member.getAge() = " + member.getAge());
        System.out.println("member.getGender() = " + member.getGender());
        System.out.println("member.getGroup() = " + member.getGroup());

        assertThat(saveId).isEqualTo(1L);


    }

    @Test
    @DisplayName("groupId로 member 찾기")
    void findAllMemberByGroupId() {
        List<Member> members = memberService.findMembersByGroupId(53L);
        for (Member member : members) {
            System.out.println("member = " + member.getName() + " group_id= " + member.getGroup().getId());
        }
    }
}