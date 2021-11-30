package visitor;

import user.Premium;
import user.User;
import entertainment.Show;

import java.util.ArrayList;
import java.util.Comparator;

public class FavoriteVisitor implements Visitor {
    private final ArrayList<Show> unseenShows;
    private String result;

    public FavoriteVisitor(final ArrayList<Show> unseenShows) {
        this.unseenShows = unseenShows;
        this.result = null;
    }

    /**
     * saves an error message, a normal user does not have
     * permission to use this type of recommendation
     */
    @Override
    public void visit(final User user) {
        result = "FavoriteRecommendation cannot be applied!";
    }

    /**
     * saves the result of favorite recommendation for a premium user
     */
    @Override
    public void visit(final Premium premium) {
        unseenShows.sort(new Comparator<Show>() {
            @Override
            public int compare(final Show o1, final Show o2) {
                return (o1.getFavourites() - o2.getFavourites()) * (-1);
            }
        });

        result = "FavoriteRecommendation result: " + unseenShows.get(0).getTitle();
    }

    /**
     * @return the result of a favorite recommendation
     */
    public String getResult() {
        return result;
    }
}
