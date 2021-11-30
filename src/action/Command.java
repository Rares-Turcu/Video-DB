package action;

import common.Constants;
import fileio.Writer;
import org.json.simple.JSONObject;
import user.User;
import entertainment.Show;

import java.io.IOException;

public class Command extends Action {
    private final User user;
    private final String type;
    private final Show show;
    private final double grade;
    private final int season;

    public Command(final int actionId, final User user, final String type,
                   final Show show, final double grade, final int season) {
        super(actionId);
        this.user = user;
        this.type = type;
        this.show = show;
        this.grade = grade;
        this.season = season;
    }

    /**
     * @param fileWriter for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    @Override
    public JSONObject work(final Writer fileWriter) throws IOException {
        switch (type) {
            case Constants.FAVORITE:
                return fileWriter.writeFile(super.getActionId(), null, favourite());
            case Constants.VIEW:
                return fileWriter.writeFile(super.getActionId(), null, view());
            case Constants.RATING:
                return fileWriter.writeFile(super.getActionId(), null, rate());
            default:
                return null;
        }
    }

    /**
     * @return a string used for output file
     */
    public String rate() {
        String key = show.getTitle();

        if (season != 0) {
            key = key + " " + season;
        }

        if (user.getHistory().containsKey(show.getTitle())) {
            if (user.getRatings().containsKey(key)) {
                return "error -> " + show.getTitle() + " has been already rated";
            }
            user.getRatings().put(key, grade);
            show.rate(this.season, this.grade);

            return "success -> " + show.getTitle() + " was rated with "
                    + grade + " by " + user.getUsername();
        }

        return "error -> " + show.getTitle() + " is not seen";
    }

    /**
     * @return a string used for output file
     */
    public String view() {
        if (user.getHistory().containsKey(show.getTitle())) {
            user.getHistory().put(show.getTitle(), user.getHistory().get(show.getTitle()) + 1);
        } else {
            user.getHistory().put(show.getTitle(), 1);
        }
        return "success -> " + show.getTitle() + " was viewed with total views of "
                + user.getHistory().get(show.getTitle());
    }

    /**
     * @return a string used for output file
     */
    public String favourite() {
        if (user.getHistory().containsKey(show.getTitle())) {
            if (user.getFavoriteMovies().contains(show.getTitle())) {
                return "error -> " + show.getTitle() + " is already in favourite list";
            }

            user.getFavoriteMovies().add(show.getTitle());
            return "success -> " + show.getTitle() + " was added as favourite";
        }
        return "error -> " + show.getTitle() + " is not seen";
    }
}
