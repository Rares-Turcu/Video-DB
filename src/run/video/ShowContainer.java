package run.video;

import common.Constants;
import fileio.*;
import run.user.Premium;
import run.user.User;

import java.util.ArrayList;

public class ShowContainer {
    private final ArrayList<Movie> movies;
    private final ArrayList<Serial> serials;

    public ShowContainer(Input input) {
        this.movies = new ArrayList<>();
        this.serials = new ArrayList<>();

        for (MovieInputData m : input.getMovies()) {
            movies.add(new Movie(m.getTitle(), m.getYear(), m.getGenres(), m.getCast(), m.getDuration()));
        }

        for(SerialInputData s : input.getSerials()) {
            serials.add(new Serial(s.getTitle(), s.getYear(), s.getGenres(), s.getCast(), s.getNumberSeason(), s.getSeasons()));
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
