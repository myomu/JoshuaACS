package site.joshua.acs.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.joshua.acs.domain.Attendance;
import site.joshua.acs.domain.Member;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AttendanceRepository {

    private final EntityManager em;

    public void save(Attendance attendance) {
        em.persist(attendance);
    }

    public Attendance findOne(Long id) {
        return em.find(Attendance.class, id);
    }

    public List<Attendance> findAll() {
        return em.createQuery("select a from Attendance a", Attendance.class)
                .getResultList();
    }

    public List<LocalDateTime> findNoDuplicateDate() {
        return em.createQuery("select a.attendance_date from Attendance a group by a.attendance_date order by a.attendance_date desc", LocalDateTime.class)
                .getResultList();
    }

    public List<Attendance> findAllByDateTime(LocalDateTime dateTime) {
        return em.createQuery("select a from Attendance a where a.attendance_date = :dateTime", Attendance.class)
                .setParameter("dateTime", dateTime)
                .getResultList();
    }

}
