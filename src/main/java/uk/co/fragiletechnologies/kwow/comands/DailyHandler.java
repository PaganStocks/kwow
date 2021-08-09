package uk.co.fragiletechnologies.kwow.comands;

import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import uk.co.fragiletechnologies.kwow.data.Artist;
import uk.co.fragiletechnologies.kwow.data.ArtistsRepository;

public class DailyHandler implements MessageHandler {

    private final ArtistsRepository artistsRepository;

    public DailyHandler(ArtistsRepository artistsRepository) {
        this.artistsRepository = artistsRepository;
    }

    @Override
    public void handleMessage(String message, MessageChannel messageChannel) {
        // Get artists
        Artist artists = artistsRepository.randomArtist();
        messageChannel.createEmbed(embedSpec -> createEmbedForDrop(embedSpec, artists)).block();
    }

    @Override
    public boolean supportsMessage(String message) {
        return message.startsWith(PREFIX + "daily");
    }

    private static void createEmbedForDrop(EmbedCreateSpec embedCreateSpec, Artist artist) {
        embedCreateSpec.setTitle("Your Daily");
        embedCreateSpec.addField(artist.getName(), artist.getName() , true);

        embedCreateSpec
                .setImage("https://storage.googleapis.com/kwow-images/Mark-nct127-v1.png");
    }
}
