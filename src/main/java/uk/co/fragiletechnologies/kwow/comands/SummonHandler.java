package uk.co.fragiletechnologies.kwow.comands;


import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import uk.co.fragiletechnologies.kwow.data.Artist;
import uk.co.fragiletechnologies.kwow.data.ArtistsRepository;

import static java.lang.String.format;

/**
 * allows a player to summon a card that will go into their inventory
 * using the command !summon
 *
 */
public class SummonHandler implements MessageHandler{

    private final ArtistsRepository artistsRepository;

    public SummonHandler(ArtistsRepository artistsRepository) {this.artistsRepository = artistsRepository; }

    @Override
    public void handleMessage(Message message, MessageChannel messageChannel) {
        Artist artists = artistsRepository.randomArtist();
        messageChannel.createEmbed(embedSpec -> createEmbedForSummon(embedSpec, artists)).block();
    }

    @Override
    public boolean supportsMessage(Message message) { return message.getContent().startsWith(PREFIX + "summon");}

    private void createEmbedForSummon(EmbedCreateSpec embedCreateSpec, Artist artist) {
        embedCreateSpec.setTitle("You have summoned an artist");
        embedCreateSpec.addField(" Congratulations!" , format("You have debuted: \n %s %s's %s", starsForArtist(artist), artist.getGroup().getName(), artist.getName()) , true);

        embedCreateSpec
                .setImage(artist.getImage());
    }

    private String starsForArtist(Artist artist) {
        return ("⭐️").repeat(artist.getRarity());
    }

}
