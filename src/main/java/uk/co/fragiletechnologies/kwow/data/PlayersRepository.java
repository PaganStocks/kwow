package uk.co.fragiletechnologies.kwow.data;

import uk.co.fragiletechnologies.kwow.exception.InsufficientBalanceException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    public void transferBalance(long currentUserId, long targetUser, int amount) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Player current = findByDiscordId(currentUserId);
        Player target = findByDiscordId(targetUser);

        if (current.getBalance() < amount) {
            transaction.rollback();
            throw new InsufficientBalanceException(amount, current.getBalance());
        }

        long newCurrentBalance = current.getBalance() - amount;
        long newTargetBalance = target.getBalance() + amount;

        current.setBalance(newCurrentBalance);
        target.setBalance(newTargetBalance);
        entityManager.persist(current);
        entityManager.persist(target);
        transaction.commit();
    }
}
