package site.joshua.acs.dto;

import lombok.Getter;
import lombok.Setter;
import site.joshua.acs.domain.Member;

@Getter @Setter
public class MemberListDTO {

    private Member member;
    private Double attendanceRate;

    public MemberListDTO(Member member, Double attendanceRate) {
        this.member = member;
        this.attendanceRate = attendanceRate;
    }
}
