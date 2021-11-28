package run.user;

import run.video.Show;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

public class Premium extends User {
    public Premium(final String username, final Map<String, Integer> history, final ArrayList<String> favoriteMovies) {
        super(username, history, favoriteMovies);
    }

    @Override
    public String recommendationPopular(ArrayList<Show> unseenShows, String popularGenre) {

        for (Show s : unseenShows) {
            if (s.getGenres().contains(popularGenre)) {
                return "PopularRecommendation result: " + s.getTitle();
            }
        }

        return "PopularRecommendation cannot be applied!";
    }

    @Override
    public String recommendationFavorite(ArrayList<Show> unseenShows) {

        unseenShows.sort(new Comparator<Show>() {
            @Override
            public int compare(Show o1, Show o2) {
                return (o1.getFavourites() - o2.getFavourites()) * (-1);
            }
        });

        return "FavoriteRecommendation result: " + unseenShows.get(0).getTitle();
    }

    @Override
    public String recommendationSearch(ArrayList<Show> unseenShows) {

        unseenShows.sort(new Comparator<Show>() {
            @Override
            public int compare(Show o1, Show o2) {
                if (o1.getNote() == o2.getNote()) {
                    return o1.getTitle().compareTo(o2.getTitle());
                }
                if (o1.getNote() < o2.getNote()) {
                    return -1;
                }
                return 0;
            }
        });

        return "SearchRecommendation result: " + unseenShows;
    }

    @Override
    public String toString() {
        return getUsername();
    }
}
