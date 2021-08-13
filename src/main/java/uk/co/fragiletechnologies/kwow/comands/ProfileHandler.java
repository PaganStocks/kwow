package uk.co.fragiletechnologies.kwow.comands;

import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.MessageChannel;
import uk.co.fragiletechnologies.kwow.data.PlayersRepository;

import static java.lang.String.format;

/**
 * lets the player show their profile using !profile or !p
 * also allows them to show another players profile by adding the players @ or ID after the command
 * <p>
 * The profile should include the players:
 * name
 * description
 * balance
 * total number of cards
 * favorite Card
 */
public class ProfileHandler implements MessageHandler {

    private final PlayersRepository playersRepository;

    public ProfileHandler(PlayersRepository playersRepository) {
        this.playersRepository = playersRepository;
    }

    @Override
    public void handleMessage(Message message, MessageChannel messageChannel) {
        long discordId = message.getAuthor().map(User::getId).map(Snowflake::asLong).orElseThrow();
        long balance = playersRepository.findByDiscordId(discordId).getBalance();
        messageChannel.createMessage(format("Your balance is %d", balance)).block();
    }

    @Override
    public boolean supportsMessage(Message message) {
        return message.getContent().startsWith(PREFIX + "profile")|| message.getContent().startsWith(PREFIX + "p");
    }
}
