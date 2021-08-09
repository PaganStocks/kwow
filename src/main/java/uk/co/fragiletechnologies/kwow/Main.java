package uk.co.fragiletechnologies.kwow;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.flywaydb.core.Flyway;
import uk.co.fragiletechnologies.kwow.comands.*;
import uk.co.fragiletechnologies.kwow.data.Artist;
import uk.co.fragiletechnologies.kwow.data.ArtistsRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.UUID;


public class Main {
    public static void main(String[] args) {
        final String token = args[0];

        Flyway flyway = Flyway.configure().dataSource("jdbc:postgresql://34.142.42.25:5432/kwow", "kwow", "smashsomeshit").load();
        flyway.migrate();

        EntityManagerFactory entityManagerFactory = createEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        ArtistsRepository artistsRepository = new ArtistsRepository(entityManager);

        final DiscordClient client = DiscordClient.create(token);
        final GatewayDiscordClient gateway = client.login().block();

        List<MessageHandler> messageHandlers = List.of(new PingHandler(), new DailyHandler(artistsRepository), new DropHandler());

        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            String message = event.getMessage().getContent();
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
