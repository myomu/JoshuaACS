package site.joshua.acs.form;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class AttendanceForm {

    // 회원의 id를 List 에 담아 editAttendanceForm 으로 전달
    private List<Long> memberIds;

}
