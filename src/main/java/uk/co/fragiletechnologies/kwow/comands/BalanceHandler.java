package uk.co.fragiletechnologies.kwow.comands;

import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import uk.co.fragiletechnologies.kwow.data.PlayersRepository;

import static java.lang.String.format;

/**
 * Shows the players balance using the command !balance or !bal
 * @author      Pagan Stocks
 */
public class BalanceHandler implements MessageHandler {

    private final PlayersRepository playersRepository;

    public BalanceHandler(PlayersRepository playersRepository) {
        this.playersRepository = playersRepository;
    }

    @Override
    public void handleMessage(Message message, MessageChannel messageChannel) {
        long discordId = message.getAuthor().map(User::getId).map(Snowflake::asLong).orElseThrow();
        long balance = playersRepository.findByDiscordId(discordId).getBalance();
        messageChannel.createEmbed(embedSpec -> createEmbedForBal(embedSpec, balance)).block();
    }

    @Override
    public boolean supportsMessage(Message message) {
        String content = message.getContent();
        return content.startsWith(PREFIX + "Balance") || content.startsWith(PREFIX + "bal");
    }

    public void createEmbedForBal(EmbedCreateSpec embedCreateSpec, long balance) {
        embedCreateSpec.addField(" balance", format("Your balance is ** %d Quid** <:Quid:874742378014064750>", balance), true);
    }
}
