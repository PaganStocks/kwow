package uk.co.fragiletechnologies.kwow.comands;

import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import uk.co.fragiletechnologies.kwow.data.Player;
import uk.co.fragiletechnologies.kwow.data.PlayersRepository;


public class BeginHandler implements MessageHandler {

    private final PlayersRepository playersRepository;

    public BeginHandler(PlayersRepository playersRepository) {
        this.playersRepository = playersRepository;
    }

    @Override
    public void handleMessage(Message message, MessageChannel messageChannel) {
        Player entity = new Player();
        entity.setDiscordId(getAuthorId(message));
        playersRepository.createUser(entity);
        messageChannel.createEmbed(this::createEmbedForBegin).block();
    }

    private Long getAuthorId(Message message) {
        return message.getAuthor().map(discord4j.core.object.entity.User::getId).map(Snowflake::asLong).orElseThrow();
    }

    @Override
    public boolean supportsMessage(Message message) {
        return message.getContent().equalsIgnoreCase(PREFIX + "begin");
    }

    public void createEmbedForBegin(EmbedCreateSpec embedCreateSpec){
        embedCreateSpec.addField(" begin", "You have set up your user, you can now play the bot", true);
    }

}


