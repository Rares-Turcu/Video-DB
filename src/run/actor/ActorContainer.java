package run.actor;

import fileio.ActorInputData;
import fileio.Input;

import java.util.ArrayList;

public class ActorContainer {
    private final ArrayList<Actor> actors;

    public ActorContainer(Input input) {
        this.actors = new ArrayList<>();

        for(ActorInputData a : input.getActors()) {
            actors.add(new Actor(a.getName(), a.getCareerDescription(), a.getFilmography(), a.getAwards()));
        }
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

    public Actor getActor(String name) {
        for (Actor a : actors) {
            if(a.getName().equals(name)) {
                return a;
            }
        }

        return null;
    }
}
