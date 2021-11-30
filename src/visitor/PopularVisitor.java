package visitor;

import entertainment.Genre;
import user.Premium;
import user.User;
import entertainment.Show;

import java.util.ArrayList;

public class PopularVisitor implements Visitor {
    private final ArrayList<Show> unseenShows;
    private final Genre popularGenre;
    private String result;

    public PopularVisitor(final ArrayList<Show> unseenShows, final Genre popularGenre) {
        this.unseenShows = unseenShows;
        this.popularGenre = popularGenre;
        this.result = null;
    }

    /**
     * saves an error message, a normal user does not have
     * permission to use this type of recommendation
     */
    @Override
    public void visit(final User user) {
        result = "PopularRecommendation cannot be applied!";
    }

    /**
     * saves the result of popular recommendation for a premium user
     */
    @Override
    public void visit(final Premium premium) {
        for (Show s : unseenShows) {
            if (s.getGenres().contains(popularGenre)) {
                result = "PopularRecommendation result: " + s.getTitle();
                break;
            }
        }
    }

    /**
     * @return the result of a popular recommendation
     */
    public String getResult() {
        return result;
    }
}
