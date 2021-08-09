package uk.co.fragiletechnologies.kwow.comands;

import discord4j.core.object.entity.channel.MessageChannel;

/**
 * An interface that should be implemented by classes reacting to a message.
 * This provides a common way of handling messages & allows implementing classes to indicate what messages they support.
 * @author      Pagan Stocks
 */
public interface MessageHandler {
    String PREFIX = "!";

    void handleMessage(String message, MessageChannel messageChannel);

    boolean supportsMessage(String message);
}
