package run.video;

import entertainment.Season;
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
        super.rate(season, grade);
        ratings.add(grade);
        calculateNote();

        System.out.println("Filmul are media: " + this.note);

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
        System.out.println("I will tottally tally for " + getTitle());

        favourites = 0;

        for(User u : users.getUsers()) {
            if(u.getFavoriteMovies().contains(getTitle())) {
                favourites++;
            }
        }

        System.out.println("favorites amount " + favourites + "\n\n\n");
    }

    @Override
    public void tallyViews(UserContainer users) {

        views = 0;

        System.out.println("I will tottally tally views for " + getTitle());

        for(User u : users.getUsers()) {
            if(u.getHistory().containsKey(getTitle())) {
                views += u.getHistory().get(getTitle());
            }
        }

        System.out.println("views amount " + views + "\n\n\n");
    }

    @Override
    public String toString() {
        return getTitle();
    }


}
