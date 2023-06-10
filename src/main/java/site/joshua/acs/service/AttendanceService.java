package site.joshua.acs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.joshua.acs.domain.Attendance;
import site.joshua.acs.repository.AttendanceRepository;
import java.time.LocalDateTime;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    /**
     * 출석 추가
     */
    @Transactional
    public Long addAttendance(Attendance attendance) {
        attendanceRepository.save(attendance);
        return attendance.getId();
    }

    /**
     * 출석들을 찾아 List 로 반환
     */
    public List<Attendance> findAttendances() {
        return attendanceRepository.findAll();
    }

    /**
     * attendanceId 에 해당하는 출석을 찾는다.
     */
    public Attendance findOne(Long attendanceId) {
        return attendanceRepository.findOne(attendanceId);
    }

    /**
     * 중복되지 않는 날짜를 List 로 반환. 내림차순 정렬로 보낸다.
     */
    public List<LocalDateTime> findNoDuplicateDate() {
        return attendanceRepository.findNoDuplicateDate();
    }

    /**
     * dateTime 에 해당하는 출석들을 찾아 List 로 반환
     */
    public List<Attendance> findAttendancesByDateTime(LocalDateTime dateTime) {
        return attendanceRepository.findAllByDateTime(dateTime);
    }

    /**
     * 출석 삭제
     */
    @Transactional
    public Long deleteAttendance(Long attendanceId) {
        Attendance attendance = attendanceRepository.findOne(attendanceId);
        attendanceRepository.delete(attendance);
        return attendance.getId();
    }
}
