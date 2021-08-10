package uk.co.fragiletechnologies.kwow.comands;

import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import uk.co.fragiletechnologies.kwow.data.Artist;
import uk.co.fragiletechnologies.kwow.data.ArtistsRepository;

import java.util.List;

import static java.lang.String.format;

public class WeeklyHandler implements MessageHandler {

    private final ArtistsRepository artistsRepository;

    public WeeklyHandler(ArtistsRepository artistsRepository) {
        this.artistsRepository = artistsRepository;
    }

    @Override
    public void handleMessage(String message, MessageChannel messageChannel) {
        List<Artist> artists = artistsRepository.randomArtists(2);
        messageChannel.createEmbed(embedSpec -> createEmbedForWeekly(embedSpec, artists)).block();

    }

    @Override
    public boolean supportsMessage(String message) {
        return message.startsWith(PREFIX + "weekly");
    }

    private void createEmbedForWeekly(EmbedCreateSpec embedCreateSpec, List<Artist> artists) {
        embedCreateSpec.setTitle("Your Weekly!");
        artists.forEach(artist -> embedCreateSpec.addField(format("Card %d", artists.indexOf(artist) + 1),format("Congratulations! You have debuted: \n %s %s's %s", starsForArtist(artist), artist.getGroup().getName(), artist.getName()), true));
        embedCreateSpec
                .setImage(artists.get(0).getImage());
    }

    private String starsForArtist(Artist artist) {
        return ("⭐️").repeat(artist.getRarity());
    }
}
