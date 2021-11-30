package entertainment;

import actor.Actor;
import user.User;
import user.UserContainer;

import java.util.ArrayList;

public class Serial extends Show {
    private final int numberOfSeasons;
    private final ArrayList<Season> seasons;

    private double note;
    private int views;
    private int favourites;

    public Serial(final String title, final int year, final ArrayList<Genre> genres,
                  final ArrayList<Actor> cast, final ArrayList<String> mentions,
                  final int numberOfSeasons, final ArrayList<Season> seasons) {
        super(title, year, genres, cast, mentions);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
        this.note = 0;
        this.views = 0;
        this.favourites = 0;
    }

    /**
     * @return duration
     */
    public int getDuration() {
        int time = 0;
        for (Season s : seasons) {
            time += s.getDuration();
        }
        return time;
    }


    /**
     * @return note
     */
    @Override
    public double getNote() {
        return note;
    }

    /**
     * @return the number of views
     */
    @Override
    public int getViews() {
        return views;
    }

    /**
     * @return the number of favourites
     */
    @Override
    public int getFavourites() {
        return favourites;
    }

    /**
     * @param season
     * @param grade
     * the method updates both the ratings list of
     * a show and of every actor in the cast when a user reviews it
     */
    @Override
    public void rate(final int season, final double grade) {
        seasons.get(season - 1).getRatings().add(grade);
        calculateNote();

        for (Actor actor : getCast()) {
            actor.calculateGrade(getTitle(), note);
        }
    }

    /**
     * calculates the note of a serial
     */
    public void calculateNote() {
        note = 0;

        for (Season season : seasons) {
            double a = 0;

            for (Double grade : season.getRatings()) {
                a += grade;
            }

            if (season.getRatings().size() != 0) {
                note += a / season.getRatings().size();
            }
        }

        note = note / seasons.size();
    }

    /**
     * @param users
     * calculates the number of times users added the serial to favorites
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
     * calculates the number of times users viewed the serial
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
