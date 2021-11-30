package entertainment;

import actor.Actor;
import user.UserContainer;

import java.util.ArrayList;

public abstract class Show {
    private final String title;
    private final int year;
    private final ArrayList<Genre> genres;
    private final ArrayList<Actor> cast;
    private final ArrayList<String> mentions;

    public Show(final String title, final int year, final ArrayList<Genre> genres,
                final ArrayList<Actor> cast, final ArrayList<String> mentions) {
        this.title = title;
        this.year = year;
        this.genres = genres;
        this.cast = cast;
        this.mentions = mentions;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return cast
     */
    public ArrayList<Actor> getCast() {
        return cast;
    }

    /**
     * @return year
     */
    public int getYear() {
        return year;
    }

    /**
     * @return genres
     */
    public ArrayList<Genre> getGenres() {
        return genres;
    }

    /**
     * @param season
     * @param grade
     * the method updates both the ratings list of
     * a show and of every actor in the cast when a user reviews it
     */
    public abstract void rate(int season, double grade);

    /**
     * @return note
     */
    public abstract double getNote();

    /**
     * @return duration
     */
    public abstract int getDuration();

    /**
     * @return views
     */
    public abstract int getViews();

    /**
     * @return favourites
     */
    public abstract int getFavourites();

    /**
     * @param users
     * calculates the number of times users added the show to favorites
     */
    public abstract void tallyFavorite(UserContainer users);

    /**
     * @param users
     * calculates the number of times users viewed the show
     */
    public abstract void tallyViews(UserContainer users);
}
