package site.joshua.acs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.joshua.acs.domain.Attendance;
import site.joshua.acs.domain.Member;
import site.joshua.acs.repository.AttendanceRepository;
import site.joshua.acs.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    @Transactional
    public Long addAttendance(Attendance attendance) {
        attendanceRepository.save(attendance);
        return attendance.getId();
    }

    public List<Attendance> findAttendances() {
        return attendanceRepository.findAll();
    }

    public Attendance findOne(Long attendanceId) {
        return attendanceRepository.findOne(attendanceId);
    }

    public List<LocalDateTime> findNoDuplicateDate() {
        return attendanceRepository.findNoDuplicateDate();
    }

    public List<Attendance> findAttendancesByDateTime(LocalDateTime dateTime) {
        return attendanceRepository.findAllByDateTime(dateTime);
    }

    @Transactional
    public Long deleteAttendance(Long attendanceId) {
        Attendance attendance = attendanceRepository.findOne(attendanceId);
        attendanceRepository.delete(attendance);
        return attendance.getId();
    }
}
