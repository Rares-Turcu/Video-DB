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

//    public UserContainer(Input input) {
//        this.users = new ArrayList<>();
//
//        for (UserInputData u : input.getUsers()) {
//            if (u.getSubscriptionType().equals(Constants.BASIC)) {
//                users.add(new User(u.getUsername(), u.getHistory(), u.getFavoriteMovies()));
//            }
//            if (u.getSubscriptionType().equals(Constants.PREMIUM)) {
//                users.add(new Premium(u.getUsername(), u.getHistory(), u.getFavoriteMovies()));
//            }
//        }
//    }
}
