package uk.co.fragiletechnologies.kwow.data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "artists")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private UUID id;

    private String name;

    private String image;

    public Artist() {
    }

    public Artist(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() { return image;}


}
