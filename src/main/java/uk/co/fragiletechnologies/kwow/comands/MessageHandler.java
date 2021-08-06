package uk.co.fragiletechnologies.kwow.comands;

import discord4j.core.object.entity.channel.MessageChannel;

public interface MessageHandler {
    String PREFIX = "!";

    void handleMessage(String message, MessageChannel messageChannel);
    boolean supportsMessage(String message);
}
