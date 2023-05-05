package site.joshua.acs.mapper;

import site.joshua.acs.domain.Attendance;
import site.joshua.acs.domain.Member;

import java.util.List;

public interface AttendanceMapper {

    Attendance getAttendance(Long attendanceId);

    void uploadAttendance(Attendance attendance);

    void updateAttendance(Attendance attendance);

    void deleteAttendance(Long attendanceId);

    void addMember(Member member);

    List<Member> getMemberList();

}
