package run.user;

import run.video.Show;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private final String username;
    private final Map<String, Integer> history;
    private final ArrayList<String> favoriteMovies;
    private final Map<String, Double> ratings;

    public User(final String username, final Map<String, Integer> history, final ArrayList<String> favoriteMovies) {
        this.username = username;
        this.history = history;
        this.favoriteMovies = favoriteMovies;
        this.ratings = new HashMap<>();
    }

    public String getUsername() {
        return username;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public Map<String, Double> getRatings() {
        return ratings;
    }

    public int getNumberOfRatings() {
        return ratings.size();
    }

    public String recommendationPopular(ArrayList<Show> unseenShows, String popularGenre) {
        return "PopularRecommendation cannot be applied!";
    }

    public String recommendationFavorite(ArrayList<Show> unseenShows) {
        return "FavoriteRecommendation cannot be applied!";
    }

    public String recommendationSearch(ArrayList<Show> unseenShows) {
        return "SearchRecommendation cannot be applied!";
    }

    @Override
    public String toString() {
        return username;
    }
}
