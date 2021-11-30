package user;

import visitor.Visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User implements Visitable {
    private final String username;
    private final Map<String, Integer> history;
    private final ArrayList<String> favoriteMovies;
    private final Map<String, Double> ratings;

    public User(final String username, final Map<String, Integer> history,
                final ArrayList<String> favoriteMovies) {
        this.username = username;
        this.history = history;
        this.favoriteMovies = favoriteMovies;
        this.ratings = new HashMap<>();
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return history
     */
    public Map<String, Integer> getHistory() {
        return history;
    }

    /**
     * @return a list of favorite movies
     */
    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    /**
     * @return a map of rated shows
     */
    public Map<String, Double> getRatings() {
        return ratings;
    }

    /**
     * @return the number of rated shows
     */
    public int getNumberOfRatings() {
        return ratings.size();
    }

    /**
     * @param v
     * an accept method used in double dispatch
     */
    @Override
    public void accept(final Visitor v) {
        v.visit(this);
    }

    /**
     * @return username
     */
    @Override
    public String toString() {
        return username;
    }
}
