package uk.co.fragiletechnologies.kwow.comands;

import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import uk.co.fragiletechnologies.kwow.data.PlayersRepository;
import uk.co.fragiletechnologies.kwow.exception.InsufficientBalanceException;

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
            long discordId = message.getAuthor().map(User::getId).map(Snowflake::asLong).orElseThrow();
            long balance = playersRepository.findByDiscordId(discordId).getBalance();
            messageChannel.createEmbed(embedSpec -> createEmbedForGiveBalance(embedSpec, amount, targetUser, balance)).block();
        } catch (NumberFormatException e) {
            messageChannel.createEmbed(embedSpec -> createEmbedForInvalidAmount(embedSpec, amountString)).block();
        } catch (InsufficientBalanceException e) {
            messageChannel.createEmbed(embedSpec -> createEmbedForInsufficientBalance(embedSpec, e.getAmount(), e.getCurrentBalance())).block();
        }
    }

    @Override
    public boolean supportsMessage(Message message) {
        String lowerCaseMessage = message.getContent().toLowerCase();
        return lowerCaseMessage.startsWith(PREFIX + "bal give") || lowerCaseMessage.startsWith(PREFIX + "balance give");
    }

    public void createEmbedForGiveBalance(EmbedCreateSpec embedCreateSpec, int amount, Long targetUser, Long  balance){
        embedCreateSpec.addField( "Balance Transfer", String.format("You have given ** %s Quid** <:Quid:874742378014064750> to %s \n Your new balance is ** %s Quid** <:Quid:874742378014064750> ",  amount, targetUser , balance), true);
    }

    public void createEmbedForInvalidAmount(EmbedCreateSpec embedCreateSpec, String amountString){
        embedCreateSpec.addField( "Insufficient Funds", String.format("'%s' is not a valid amount", amountString), true);
    }

    public void createEmbedForInsufficientBalance(EmbedCreateSpec embedCreateSpec, int amount, Long balance){
        embedCreateSpec.addField( "Insufficient Funds", String.format("You tried to give a player** %s Quid** <:Quid:874742378014064750> but you only have **%s Quid** <:Quid:874742378014064750>",  amount , balance), true);
    }
}

