package uk.co.fragiletechnologies.kwow;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import org.flywaydb.core.Flyway;
import uk.co.fragiletechnologies.kwow.comands.*;
import uk.co.fragiletechnologies.kwow.data.ArtistsRepository;
import uk.co.fragiletechnologies.kwow.data.PlayersRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        final String token = args[0];

        Flyway flyway = Flyway.configure().dataSource("jdbc:postgresql://34.142.42.25:5432/kwow", "kwow", "smashsomeshit").load();
//        Flyway flyway = Flyway.configure().dataSource("jdbc:postgresql://localhost:5432/kwow", "postgres", "foo").load();
        flyway.migrate();

        EntityManagerFactory entityManagerFactory = createEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        ArtistsRepository artistsRepository = new ArtistsRepository(entityManager);
        PlayersRepository playersRepository = new PlayersRepository(entityManager);

        final DiscordClient client = DiscordClient.create(token);
        final GatewayDiscordClient gateway = client.login().block();

        List<MessageHandler> messageHandlers = List.of(
                new PingHandler(),
                new BalanceHandler(playersRepository),
                new DailyHandler(artistsRepository),
                new DropHandler(artistsRepository),
                new WeeklyHandler(artistsRepository),
                new ProfileHandler(playersRepository),
                new SummonHandler(artistsRepository),
                new GiveHandler(playersRepository),
                new BeginHandler(playersRepository)
        );

        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            Message message = event.getMessage();
            MessageHandler handler = messageHandlers.stream()
                    .filter(h -> h.supportsMessage(message))
                    .findAny()
                    .orElse(new NotFoundHandler());
            handler.handleMessage(message, event.getMessage().getChannel().block());
        });

        gateway.onDisconnect().block();
    }

    private static EntityManagerFactory createEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("kwow");
    }
}
