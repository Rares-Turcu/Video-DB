package run.video;

import run.actor.Actor;
import run.user.User;
import run.user.UserContainer;

import java.util.ArrayList;
import java.util.List;

public class Movie extends Show {
    private final int duration;
    private List<Double> ratings;

    private double note;
    private int views;
    private int favourites;

    public Movie(String title, int year, ArrayList<String> genres, ArrayList<Actor> cast, ArrayList<String> mentions, int duration) {
        super(title, year, genres, cast, mentions);
        this.duration = duration;
        this.ratings = new ArrayList<>();
        this.note = 0;
        this.views = 0;
        this.favourites = 0;
    }

    @Override
    public double getNote() {
        return note;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public int getFavourites() {
        return favourites;
    }

    @Override
    public int getViews() {
        return views;
    }

    @Override
    public void rate(int season, double grade) {
        ratings.add(grade);
        calculateNote();

        for (Actor actor : getCast()) {
            actor.calculateGrade(getTitle(), note);
        }
    }

    public void calculateNote() {
        note = 0;

        for(Double grade : ratings) {
            note += grade;
        }

        note = note / ratings.size();
    }

    @Override
    public void tallyFavorite(UserContainer users) {
        favourites = 0;

        for(User u : users.getUsers()) {
            if(u.getFavoriteMovies().contains(getTitle())) {
                favourites++;
            }
        }
    }

    @Override
    public void tallyViews(UserContainer users) {
        views = 0;

        for(User u : users.getUsers()) {
            if(u.getHistory().containsKey(getTitle())) {
                views += u.getHistory().get(getTitle());
            }
        }
    }

    @Override
    public String toString() {
        return getTitle();
    }


}
