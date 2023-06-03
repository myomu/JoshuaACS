package site.joshua.acs.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.joshua.acs.domain.MinutesFile;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MinutesFileRepository {

    private final EntityManager em;

    public void save(MinutesFile minutesFile) {
        em.persist(minutesFile);
    }

    public MinutesFile fineOne(Long id) {
        return em.find(MinutesFile.class, id);
    }

    public List<MinutesFile> findAll() {
        return em.createQuery("select mf from MinutesFile mf order by mf.date desc", MinutesFile.class)
                .getResultList();
    }

    public void delete(MinutesFile minutesFile) {
        em.remove(minutesFile);
    }

}
