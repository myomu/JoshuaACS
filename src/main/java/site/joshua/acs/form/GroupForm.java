package site.joshua.acs.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter // form 즉, DTO 는 Setter 가 필수이다.. 이거 빼먹어서 계속 데이터 값이 null 로 들어갔더 거였음..;;
public class GroupForm {

    @NotEmpty(message = "조이름은 필수입니다.")
    private String name;

}
