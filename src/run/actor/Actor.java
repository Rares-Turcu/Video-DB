package run.actor;

import actor.ActorsAwards;

import java.util.ArrayList;
import java.util.Map;

public class Actor {
    private final String name;
    private final String description;
    private final ArrayList<String> filmography;
    private final Map<ActorsAwards, Integer> awards;

    public Actor(final String name, final String description, final ArrayList<String> filmography, final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.description = description;
        this.filmography = filmography;
        this.awards = awards;
    }
}
