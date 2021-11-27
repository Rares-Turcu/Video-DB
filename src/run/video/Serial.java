package run.video;

import entertainment.Season;
import run.actor.Actor;
import run.user.User;
import run.user.UserContainer;

import java.util.ArrayList;

public class Serial extends Show {
    private final int numberOfSeasons;
    private final ArrayList<Season> seasons;

    private double note;
    private int views;
    private int favourites;

    public Serial(String title, int year, ArrayList<String> genres, ArrayList<Actor> cast, ArrayList<String> mentions, int numberOfSeasons, ArrayList<Season> seasons) {
        super(title, year, genres, cast, mentions);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
        this.note = 0;
        this.views = 0;
        this.favourites = 0;
    }

    public int getDuration() {
        int time = 0;
        for(Season s : seasons) {
            time += s.getDuration();
        }
        return time;
    }

    @Override
    public double getNote() {
        return note;
    }

    @Override
    public int getViews() {
        return views;
    }

    @Override
    public int getFavourites() {
        return favourites;
    }

    @Override
    public void rate(int season, double grade) {
        seasons.get(season - 1).getRatings().add(grade);
        super.rate(season, grade);
        calculateNote();

        System.out.println("Serialul are media : " + this.note);

        for (Actor actor : getCast()) {
            actor.calculateGrade(getTitle(), note);
        }
    }

    public void calculateNote() {
        note = 0;

        for (Season season : seasons) {
            double a = 0;

            for (Double grade : season.getRatings()) {
                a += grade;
            }

            if(season.getRatings().size() != 0) {
                note += a / season.getRatings().size();
            }
        }

        note = note / seasons.size();
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
