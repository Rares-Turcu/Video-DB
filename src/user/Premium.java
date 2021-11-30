package user;

import visitor.Visitor;

import java.util.ArrayList;
import java.util.Map;

public class Premium extends User {
    public Premium(final String username, final Map<String, Integer> history,
                   final ArrayList<String> favoriteMovies) {
        super(username, history, favoriteMovies);
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
        return getUsername();
    }
}
