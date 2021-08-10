package uk.co.fragiletechnologies.kwow.comands;

import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import uk.co.fragiletechnologies.kwow.data.Artist;
import uk.co.fragiletechnologies.kwow.data.ArtistsRepository;

import java.util.List;

import static java.lang.String.format;

/** allows the player to drop 3 card choices
 * the player who dropped will get a 10 second head start to claim one of the cards before any other
 * players can react to claim the left over cards
 */
public class DropHandler implements MessageHandler {

    private final ArtistsRepository artistsRepository;

    public DropHandler(ArtistsRepository artistsRepository) {this.artistsRepository = artistsRepository;}

    @Override
    public void handleMessage(String message, MessageChannel messageChannel) {
        // Get artists
        List<Artist> artists = artistsRepository.randomArtists(3);
        messageChannel.createEmbed(embedSpec -> createEmbedForDrop(embedSpec, artists)).block();



    }

    @Override
    public boolean supportsMessage(String message) {
        return message.startsWith(PREFIX + "drop");

    }

    private static void createEmbedForDrop(EmbedCreateSpec embedCreateSpec, List<Artist> artists) {
        embedCreateSpec.setTitle("You have dropped:");

        artists.forEach(artist -> embedCreateSpec.addField(format("Card %d", artists.indexOf(artist) + 1),format(" \n %s %s's %s", starsForArtist(artist), artist.getGroup().getName(), artist.getName()), true));

        embedCreateSpec
                .setImage(artists.get(0).getImage());

    }

    private static String starsForArtist(Artist artist) {
        return ("⭐️").repeat(artist.getRarity());
    }

}
