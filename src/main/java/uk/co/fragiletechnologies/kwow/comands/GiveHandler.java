package uk.co.fragiletechnologies.kwow.comands;

import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.MessageChannel;
import uk.co.fragiletechnologies.kwow.data.PlayersRepository;

import java.util.ArrayList;

/**
 * allows you to give Quids to other players using
 * !bal/balance give @player amount
 */
public class GiveHandler implements MessageHandler {

    private final PlayersRepository playersRepository;

    public GiveHandler(PlayersRepository playersRepository) {
        this.playersRepository = playersRepository;
    }

    @Override
    public void handleMessage(Message message, MessageChannel messageChannel) {
        String[] messageParts = message.getContent().split(" ");
        String amountString = messageParts[messageParts.length - 1];

        long currentUser = message.getAuthor().map(User::getId).map(Snowflake::asLong).orElseThrow();
        long targetUser = new ArrayList<>(message.getUserMentionIds()).get(0).asLong();

        try {
            int amount = Integer.parseInt(amountString);
            playersRepository.transferBalance(currentUser, targetUser, amount);
        } catch (NumberFormatException e) {
            messageChannel.createMessage(String.format("'%s' is not a valid amount", amountString)).block();
        }
    }

    @Override
    public boolean supportsMessage(Message message) {
        String lowerCaseMessage = message.getContent().toLowerCase();
        return lowerCaseMessage.startsWith(PREFIX + "bal give") || lowerCaseMessage.startsWith(PREFIX + "balance give");
    }
}
