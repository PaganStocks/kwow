package uk.co.fragiletechnologies.kwow;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import discord4j.core.*;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.MessageCreateSpec;
import uk.co.fragiletechnologies.kwow.data.Group;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;


public class Main {
    public static void main(String[] args) {
        final String token = args[0];
        final DiscordClient client = DiscordClient.create(token);
        final GatewayDiscordClient gateway = client.login().block();

        Gson gson = new Gson();
        Type groupTypeToken = new TypeToken<List<Group>>(){}.getType();
        InputStream is = Main.class.getClassLoader().getResourceAsStream("bts.json");
        List<Group> groups = gson.fromJson(new InputStreamReader(is), groupTypeToken);

        Random randomIndex = new Random();


        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            final Message message = event.getMessage();
            if ("!ping".equals(message.getContent())) {
                final MessageChannel channel = message.getChannel().block();
                channel.createMessage("Pong!").block();
            }
//            if ("!drop".equals(message.getContent())) {
//              final MessageChannel channel = message.getChannel().block();
//            channel.createMessage( groups[randomIndex.nextInt(groups.length)]).block();
//            }

        });

        gateway.onDisconnect().block();

    }
}
