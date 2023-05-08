package site.joshua.acs.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import site.joshua.acs.domain.Gender;
import site.joshua.acs.domain.Group;

@Getter @Setter // Setter 필수!!
public class MemberForm {

    @NotEmpty(message = "이름은 필수 입니다")
    private String name;

    private int age;
    private Gender gender;
    private Group group;
}
