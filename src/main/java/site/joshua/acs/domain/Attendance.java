package site.joshua.acs.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Attendance {
    private Long attendanceId;
    private LocalDateTime createDate;
    private Map<String, String> memberMap;

//    public Attendance(Long attendanceId, LocalDateTime createDate, Map<String, String> memberMap) {
//        this.attendanceId = attendanceId;
//        this.createDate = createDate;
//        this.memberMap = memberMap;
//    }
//
//    public Long getAttendanceId() {
//        return attendanceId;
//    }
//
//    public void setAttendanceId(Long attendanceId) {
//        this.attendanceId = attendanceId;
//    }
//
//    public LocalDateTime getCreateDate() {
//        return createDate;
//    }
//
//    public void setCreateDate(LocalDateTime createDate) {
//        this.createDate = createDate;
//    }
//
//    public Map<String, String> getMemberMap() {
//        return memberMap;
//    }
//
//    public void setMemberMap(Map<String, String> memberMap) {
//        this.memberMap = memberMap;
//    }
}
