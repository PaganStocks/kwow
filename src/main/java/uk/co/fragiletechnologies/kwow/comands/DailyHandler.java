package uk.co.fragiletechnologies.kwow.comands;

import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import uk.co.fragiletechnologies.kwow.data.Artist;
import uk.co.fragiletechnologies.kwow.data.ArtistsRepository;

import static java.lang.String.format;

/**
 * once a day you can drop a card and it will go into the players inventory
 */
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
        embedCreateSpec.addField(" Congratulations!" , format("You have debuted: \n %s %s's %s", starsForArtist(artist), artist.getGroup().getName(), artist.getName()) , true);

        embedCreateSpec
                .setImage(artist.getImage());
    }

    private static String starsForArtist(Artist artist) {
        return ("⭐️").repeat(artist.getRarity());
    }
}
