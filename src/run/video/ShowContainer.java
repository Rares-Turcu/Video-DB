package run.video;

import fileio.*;
import run.actor.Actor;
import run.actor.ActorContainer;

import java.util.ArrayList;

public class ShowContainer {
    private final ArrayList<Movie> movies;
    private final ArrayList<Serial> serials;

    public ShowContainer(Input input, ActorContainer actors) {
        this.movies = new ArrayList<>();
        this.serials = new ArrayList<>();

        for (MovieInputData m : input.getMovies()) {
            ArrayList<Actor> cast = new ArrayList<>();
            ArrayList<String> mentions = new ArrayList<>();

            for(String str : m.getCast()) {
                if(actors.getActor(str) == null) {
                    mentions.add(str);
                }
                else {
                    cast.add(actors.getActor(str));
                }
            }

            movies.add(new Movie(m.getTitle(), m.getYear(), m.getGenres(), cast, mentions, m.getDuration()));
        }

        for(SerialInputData s : input.getSerials()) {
            ArrayList<Actor> cast = new ArrayList<>();
            ArrayList<String> mentions = new ArrayList<>();

            for(String str : s.getCast()) {
                if(actors.getActor(str) == null) {
                    mentions.add(str);
                }
                else {
                    cast.add(actors.getActor(str));
                }
            }

            serials.add(new Serial(s.getTitle(), s.getYear(), s.getGenres(), cast, mentions, s.getNumberSeason(), s.getSeasons()));
        }
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public ArrayList<Serial> getSerials() {
        return serials;
    }

    public Show getShow(String title) {
        for (Movie m : movies) {
            if (m.getTitle().equals(title)) {
                return m;
            }
        }
        for (Serial s : serials) {
            if (s.getTitle().equals(title)) {
                return s;
            }
        }
        return null;
    }
}
