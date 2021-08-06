package uk.co.fragiletechnologies.kwow.comands;

import discord4j.core.object.entity.channel.MessageChannel;

public class PingHandler implements MessageHandler {
    @Override
    public void handleMessage(String message, MessageChannel messageChannel) {

    }

    @Override
    public boolean supportsMessage(String message) {
        return message.startsWith(PREFIX + "ping");
    }
}
