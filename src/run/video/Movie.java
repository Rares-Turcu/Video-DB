package run.video;

import java.util.ArrayList;

public class Movie extends Show{
    public final int duration;

    public Movie(String title, int year, ArrayList<String> genres, ArrayList<String> cast, int duration) {
        super(title, year, genres, cast);
        this.duration = duration;
    }
}
