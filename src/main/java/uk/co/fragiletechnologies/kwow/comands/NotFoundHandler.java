package uk.co.fragiletechnologies.kwow.comands;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

public class NotFoundHandler implements MessageHandler {
    @Override
    public void handleMessage(Message message, MessageChannel messageChannel) {

    }

    @Override
    public boolean supportsMessage(Message message) {
        return false;
    }
}
