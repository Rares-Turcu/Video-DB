package run.video;

import entertainment.Season;

import java.util.ArrayList;

public class Serial extends Show {
    private final int numberOfSeasons;
    private final ArrayList<Season> seasons;

    public Serial(String title, int year, ArrayList<String> genres, ArrayList<String> cast, int numberOfSeasons, ArrayList<Season> seasons) {
        super(title, year, genres, cast);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
    }

}
