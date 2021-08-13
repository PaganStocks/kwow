package uk.co.fragiletechnologies.kwow.comands;

import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
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
    }

    private Long getAuthorId(Message message) {
        return message.getAuthor().map(discord4j.core.object.entity.User::getId).map(Snowflake::asLong).orElseThrow();
    }

    @Override
    public boolean supportsMessage(Message message) {
        return false;
    }
}
