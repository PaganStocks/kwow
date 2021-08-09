package uk.co.fragiletechnologies.kwow.comands;

import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import uk.co.fragiletechnologies.kwow.data.Artist;

import java.util.List;

public class DropHandler implements MessageHandler {

    @Override
    public void handleMessage(String message, MessageChannel messageChannel) {
        // Get artists
        List<Artist> artists = List.of(new Artist("Rm"), new Artist("Jin"), new Artist("Suga"));
        messageChannel.createEmbed(embedSpec -> createEmbedForDrop(embedSpec, artists)).block();
    }

    @Override
    public boolean supportsMessage(String message) {
        return message.startsWith(PREFIX + "drop");
    }

    private static void createEmbedForDrop(EmbedCreateSpec embedCreateSpec, List<Artist> artists) {
        embedCreateSpec.setTitle("Awesome drop");

        artists.forEach(artist -> embedCreateSpec.addField("Name", artist.getName(), true));

        embedCreateSpec
                .setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1200px-Image_created_with_a_mobile_phone.png");
    }


}
