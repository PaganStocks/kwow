package uk.co.fragiletechnologies.kwow.data;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ArtistsRepository {

    private final EntityManager entityManager;

    public ArtistsRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Artist> randomArtists(int n) {
        TypedQuery<Artist> query = entityManager.createQuery("FROM Artist ORDER BY RAND()", Artist.class)
                .setMaxResults(n);
        return query.getResultList();
    }

    public Artist randomArtist() {
        TypedQuery<Artist> query = entityManager.createQuery("FROM Artist ORDER BY RAND()", Artist.class)
                .setMaxResults(1);
        return query.getSingleResult();
    }
}
