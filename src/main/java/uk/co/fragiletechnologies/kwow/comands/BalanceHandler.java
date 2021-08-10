package uk.co.fragiletechnologies.kwow.comands;

import discord4j.core.object.entity.channel.MessageChannel;
/**
 * Shows the players balance using the command !balance or !bal
 *
 * also allows you to give currency to other players using
 * !bal/balance give @player amount
 * @author      Pagan Stocks
 */
public class BalanceHandler implements MessageHandler{

    @Override
    public void handleMessage(String message, MessageChannel messageChannel) {


    }
    @Override
    public boolean supportsMessage(String message) {
        return message.startsWith(PREFIX + "Balance" ) || message.startsWith(PREFIX + "bal");
    }
}
