package run.user;

import java.util.ArrayList;
import java.util.Map;

public class Premium extends User {
    public Premium(final String username, final Map<String, Integer> history, final ArrayList<String> favoriteMovies) {
        super(username, history, favoriteMovies);
    }

    @Override
    public String toString() {
        return "Premium User : " + super.getUsername();
    }
}
