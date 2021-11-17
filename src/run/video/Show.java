package run.video;

import java.util.ArrayList;

public abstract class Show {
    private final String title;
    private final int year;
    private final ArrayList<String> genres;
    private final ArrayList<String> cast;

    public Show(String title, int year, ArrayList<String> genres, ArrayList<String> cast) {
        this.title = title;
        this.year = year;
        this.genres = genres;
        this.cast = cast;
    }

    public String getTitle() {
        return title;
    }
}
