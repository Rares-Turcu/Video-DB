package entertainment;

import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import actor.Actor;
import actor.ActorContainer;

import java.util.ArrayList;

import static utils.Utils.stringToGenre;

public class ShowContainer {
    private final ArrayList<Movie> movies;
    private final ArrayList<Serial> serials;

    public ShowContainer(final Input input, final ActorContainer actors) {
        this.movies = new ArrayList<>();
        this.serials = new ArrayList<>();

        for (MovieInputData m : input.getMovies()) {
            ArrayList<Actor> cast = new ArrayList<>();
            ArrayList<String> mentions = new ArrayList<>();

            for (String str : m.getCast()) {
                if (actors.getActor(str) == null) {
                    mentions.add(str);
                } else {
                    cast.add(actors.getActor(str));
                }
            }
            ArrayList<Genre> genres = new ArrayList<>();

            for (String s : m.getGenres()) {
                genres.add(stringToGenre(s));
            }

            movies.add(new Movie(m.getTitle(), m.getYear(),
                    genres, cast, mentions, m.getDuration()));
        }

        for (SerialInputData s : input.getSerials()) {
            ArrayList<Actor> cast = new ArrayList<>();
            ArrayList<String> mentions = new ArrayList<>();

            for (String str : s.getCast()) {
                if (actors.getActor(str) == null) {
                    mentions.add(str);
                } else {
                    cast.add(actors.getActor(str));
                }
            }

            ArrayList<Genre> genres = new ArrayList<>();

            for (String str : s.getGenres()) {
                genres.add(stringToGenre(str));
            }

            serials.add(new Serial(s.getTitle(), s.getYear(), genres,
                    cast, mentions, s.getNumberSeason(), s.getSeasons()));

        }
    }

    /**
     * @return a list movies
     */
    public ArrayList<Movie> getMovies() {
        return movies;
    }

    /**
     * @return a list serials
     */
    public ArrayList<Serial> getSerials() {
        return serials;
    }

    /**
     * @param  title
     * @return a specific show by title
     */
    public Show getShow(final String title) {
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
