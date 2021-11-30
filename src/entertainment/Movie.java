package entertainment;

import actor.Actor;
import user.User;
import user.UserContainer;

import java.util.ArrayList;
import java.util.List;

public class Movie extends Show {
    private final int duration;
    private List<Double> ratings;

    private double note;
    private int views;
    private int favourites;

    public Movie(final String title, final int year, final ArrayList<Genre> genres,
                 final ArrayList<Actor> cast, final ArrayList<String> mentions,
                 final int duration) {
        super(title, year, genres, cast, mentions);
        this.duration = duration;
        this.ratings = new ArrayList<>();
        this.note = 0;
        this.views = 0;
        this.favourites = 0;
    }

    /**
     * @return the note of a movie
     */
    @Override
    public double getNote() {
        return note;
    }

    /**
     * @return duration
     */
    @Override
    public int getDuration() {
        return duration;
    }

    /**
     * @return the number of favourites
     */
    @Override
    public int getFavourites() {
        return favourites;
    }

    /**
     * @return the number of views
     */
    @Override
    public int getViews() {
        return views;
    }

    /**
     * @param season
     * @param grade
     * the method updates both the ratings list of a show
     * and of every actor in the cast when a user reviews it
     */
    @Override
    public void rate(final int season, final double grade) {
        ratings.add(grade);
        calculateNote();

        for (Actor actor : getCast()) {
            actor.calculateGrade(getTitle(), note);
        }
    }

    /**
     * calculates the note of a movie
     */
    public void calculateNote() {
        note = 0;

        for (Double grade : ratings) {
            note += grade;
        }

        note = note / ratings.size();
    }

    /**
     * @param users
     * calculates the number of times users added the movie to favorites
     */
    @Override
    public void tallyFavorite(final UserContainer users) {
        favourites = 0;

        for (User u : users.getUsers()) {
            if (u.getFavoriteMovies().contains(getTitle())) {
                favourites++;
            }
        }
    }

    /**
     * @param users
     * calculates the number of times users viewed the movie
     */
    @Override
    public void tallyViews(final UserContainer users) {
        views = 0;

        for (User u : users.getUsers()) {
            if (u.getHistory().containsKey(getTitle())) {
                views += u.getHistory().get(getTitle());
            }
        }
    }

    /**
     * @return title
     */
    @Override
    public String toString() {
        return getTitle();
    }


}
