package run.action;

import common.Constants;
import fileio.Writer;
import org.json.simple.JSONObject;
import run.user.User;
import run.video.Show;

import java.io.IOException;

// TO DO
// add rating and views for show

public class Command extends Action {
    private final User user;
    private final String type;
    private final Show show;
    private final double grade;
    private final int season;

    public Command(final int actionId, final User user, final String type, final Show show, final double grade, final int season) {
        super(actionId);
        this.user = user;
        this.type = type;
        this.show = show;
        this.grade = grade;
        this.season = season;
    }

    @Override
    public JSONObject work(Writer fileWriter) throws IOException {
        if (type.equals(Constants.FAVORITE)) {
            return fileWriter.writeFile(super.getActionId(), null, favourite());
        }
        if (type.equals(Constants.VIEW)) {
            return fileWriter.writeFile(super.getActionId(), null, view());
        }
        if (type.equals(Constants.RATING)) {
            return fileWriter.writeFile(super.getActionId(), null, rate());
        }
        return null;
    }

    public String rate() {
        String key = show.getTitle();

        if(season != 0) {
            key = key + " " + season;
        }
//        System.out.println(key);
        if(user.getHistory().containsKey(show.getTitle())) {
            if(user.getRatings().containsKey(key)) {
                return "Unknown_Command";
            }
            user.getRatings().put(key, grade);
            return "success -> " + show.getTitle() + " was rated with " + grade + " by " + user.getUsername();
        }

        return "error -> " + show.getTitle() + " is not seen";
    }


    public String view() {
        if (user.getHistory().containsKey(show.getTitle())) {
            user.getHistory().put(show.getTitle(), user.getHistory().get(show.getTitle()) + 1);
        }
        else {
            user.getHistory().put(show.getTitle(), 1);
        }
        return "success -> " + show.getTitle() + " was viewed with total views of " + user.getHistory().get(show.getTitle());
    }

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

    @Override
    public String toString() {
        return "Put in favourite for user " + user.getUsername() + " the movie " + show.getTitle();
    }
}
