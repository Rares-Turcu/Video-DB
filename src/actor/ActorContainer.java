package actor;

import fileio.ActorInputData;
import fileio.Input;

import java.util.ArrayList;

public class ActorContainer {
    private final ArrayList<Actor> actors;

    public ActorContainer(final Input input) {
        this.actors = new ArrayList<>();

        for (ActorInputData a : input.getActors()) {
            actors.add(new Actor(a.getName(), a.getCareerDescription(),
                    a.getFilmography(), a.getAwards()));
        }
    }

    /**
     * @return a list of actors
     */
    public ArrayList<Actor> getActors() {
        return actors;
    }

    /**
     * @param name
     * @return a specific actor by name
     */
    public Actor getActor(final String name) {
        for (Actor a : actors) {
            if (a.getName().equals(name)) {
                return a;
            }
        }

        return null;
    }
}
