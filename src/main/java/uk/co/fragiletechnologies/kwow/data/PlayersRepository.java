package uk.co.fragiletechnologies.kwow.data;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class PlayersRepository {

    private final EntityManager entityManager;

    public PlayersRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Player findByDiscordId(long discordId) {
        TypedQuery<Player> query = entityManager
                .createQuery("SELECT u FROM Player u WHERE u.discordId = :discordId", Player.class);
        query.setParameter("discordId", discordId);
        return query.getSingleResult();
    }

    public void createUser(Player entity) {

    }
}
