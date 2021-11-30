package visitor;

import user.Premium;
import user.User;
import entertainment.Show;

import java.util.ArrayList;
import java.util.Comparator;

public class SearchVisitor implements Visitor {
    private final ArrayList<Show> unseenShows;
    private String result;

    public SearchVisitor(final ArrayList<Show> unseenShows) {
        this.unseenShows = unseenShows;
        this.result = null;
    }

    /**
     * saves an error message, a normal user does not have
     * permission to use this type of recommendation
     */
    @Override
    public void visit(final User user) {
        result = "SearchRecommendation cannot be applied!";
    }

    /**
     * saves the result of search recommendation for a premium user
     */
    @Override
    public void visit(final Premium premium) {
        unseenShows.sort(new Comparator<Show>() {
            @Override
            public int compare(final Show o1, final Show o2) {
                if (o1.getNote() == o2.getNote()) {
                    return o1.getTitle().compareTo(o2.getTitle());
                }
                if (o1.getNote() < o2.getNote()) {
                    return -1;
                }
                return 0;
            }
        });

        result =  "SearchRecommendation result: " + unseenShows;
    }

    /**
     * @return the result of a search recommendation
     */
    public String getResult() {
        return result;
    }
}
