package uk.co.fragiletechnologies.kwow;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import org.flywaydb.core.Flyway;
import uk.co.fragiletechnologies.kwow.data.Artist;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;



public class Main {
    public static void main(String[] args) {
        final String token = args[0];

        Flyway flyway = Flyway.configure().dataSource("jdbc:postgresql://localhost:5432/kwow", "postgres", "foo").load();
        flyway.migrate();

        final DiscordClient client = DiscordClient.create(token);
        final GatewayDiscordClient gateway = client.login().block();


        Gson gson = new Gson();
        Type groupTypeToken = new TypeToken<List<Artist>>() {
        }.getType();
        InputStream is = Main.class.getClassLoader().getResourceAsStream("bts.json");
        assert is != null;
        List<Artist> artists = gson.fromJson(new InputStreamReader(is), groupTypeToken);

        Random randomIndex = new Random();
        String prefix = "!";

        assert gateway != null;
        gateway.on(MessageCreateEvent.class).subscribe(event -> {


                    final Message message = event.getMessage();

                    if ((prefix + "ping").equals(message.getContent())) {

                        final MessageChannel channel = message.getChannel().block();
                        assert channel != null;
                        channel.createMessage("Pong!").block();
                    }

                    if ((prefix + "daily").equals(message.getContent())) {
                        final MessageChannel channel = message.getChannel().block();
                        Artist artist = artists.get(randomIndex.nextInt(artists.size()));
                        assert channel != null;
                        channel.createMessage(artist.getName()).block();
                    }


                    if ((prefix + "weekly").equals(message.getContent())) {
                        final MessageChannel channel = message.getChannel().block();
                        Artist artistA = artists.get(randomIndex.nextInt(artists.size()));
                        assert channel != null;
                        channel.createMessage(artistA.getName()).block();
                        Artist artistB = artists.get(randomIndex.nextInt(artists.size()));
                        channel.createMessage(artistB.getName()).block();
                    }



                    if ((prefix + "summon").equals(message.getContent())) {
                        final MessageChannel channel = message.getChannel().block();
                        Artist artist = artists.get(randomIndex.nextInt(artists.size()));
                        assert channel != null;
                        channel.createMessage(artist.getName()).block();
                    }


                    if ((prefix + "drop").equals(message.getContent())) {
                        final MessageChannel channel = message.getChannel().block();
                        Artist artist1 = artists.get(randomIndex.nextInt(artists.size()));
                        assert channel != null;
                        channel.createMessage(artist1.getName()).block();
                        Artist artist2 = artists.get(randomIndex.nextInt(artists.size()));
                        channel.createMessage(artist2.getName()).block();
                        Artist artist3 = artists.get(randomIndex.nextInt(artists.size()));
                        channel.createMessage(artist3.getName()).block();

                    }
                }
        );

        gateway.onDisconnect().block();

    }
}
///createEmbed()