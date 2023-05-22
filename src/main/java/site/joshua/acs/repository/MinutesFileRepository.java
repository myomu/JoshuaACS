package site.joshua.acs.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.joshua.acs.domain.MinutesFile;

@Repository
@RequiredArgsConstructor
public class MinutesFileRepository {

    private final EntityManager em;

    public void save(MinutesFile minutesFile) {
        em.persist(minutesFile);
    }

}
