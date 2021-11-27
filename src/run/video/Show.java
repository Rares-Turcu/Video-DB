package run.video;

import entertainment.Season;
import run.actor.Actor;
import run.user.UserContainer;

import java.util.ArrayList;

public abstract class Show {
    private final String title;
    private final int year;
    private final ArrayList<String> genres;
    private final ArrayList<Actor> cast;
    private final ArrayList<String> mentions;

    public Show(String title, int year, ArrayList<String> genres, ArrayList<Actor> cast, ArrayList<String> mentions) {
        this.title = title;
        this.year = year;
        this.genres = genres;
        this.cast = cast;
        this.mentions = mentions;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Actor> getCast() {
        return cast;
    }

    public int getYear() {
        return year;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void rate(int season, double grade) {
        System.out.println(title + "Was graded " + grade);
        System.out.println("\n");
    }

    public abstract double getNote();

    public abstract int getDuration();

    abstract public int getViews();

    abstract public int getFavourites();

    public abstract void tallyFavorite(UserContainer users);

    public abstract void tallyViews(UserContainer users);
}
