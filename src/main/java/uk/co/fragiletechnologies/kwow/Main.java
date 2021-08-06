package uk.co.fragiletechnologies.kwow;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.flywaydb.core.Flyway;
import uk.co.fragiletechnologies.kwow.comands.*;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        final String token = args[0];

        Flyway flyway = Flyway.configure().dataSource("jdbc:postgresql://localhost:5432/kwow", "postgres", "foo").load();
        flyway.migrate();

        final DiscordClient client = DiscordClient.create(token);
        final GatewayDiscordClient gateway = client.login().block();

        List<MessageHandler> messageHandlers = List.of(new PingHandler(), new DailyHandler(), new DropHandler());

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
}
