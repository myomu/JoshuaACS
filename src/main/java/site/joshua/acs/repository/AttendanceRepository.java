package site.joshua.acs.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.joshua.acs.domain.Attendance;
import site.joshua.acs.domain.Member;

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

}
