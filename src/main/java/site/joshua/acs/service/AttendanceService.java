package site.joshua.acs.service;

import site.joshua.acs.domain.Attendance;
import site.joshua.acs.domain.Member;
import site.joshua.acs.mapper.AttendanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//Mapper 인터페이스에서 설정한 것들은 Service에서 구현한다.
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttendanceService {

    private final AttendanceMapper attendanceMapper;

    public Attendance getAttendance(Long attendanceId) {
        return attendanceMapper.getAttendance(attendanceId);
    }

    @Transactional
    public void uploadAttendance(Attendance attendance) {
        attendanceMapper.uploadAttendance(attendance);
    }

    @Transactional
    public void updateAttendance(Attendance attendance) {
        attendanceMapper.updateAttendance(attendance);
    }

    @Transactional
    public void deleteAttendance(Long attendanceId) {
        attendanceMapper.deleteAttendance(attendanceId);
    }

    @Transactional
    public void addMember(Member member) {
        attendanceMapper.addMember(member);
    }

    @Transactional
    public List<Member> getMemberList() {
        return attendanceMapper.getMemberList();
    }
}
