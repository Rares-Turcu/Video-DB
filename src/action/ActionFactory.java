package action;

import common.Constants;
import fileio.ActionInputData;
import fileio.Input;
import actor.ActorContainer;
import user.UserContainer;
import entertainment.Movie;
import entertainment.Serial;
import entertainment.ShowContainer;

import java.util.ArrayList;

public class ActionFactory {
    private final ArrayList<Action> actions;

    public ActionFactory(final Input input, final UserContainer users,
                         final ShowContainer shows, final ActorContainer actors) {
        this.actions = new ArrayList<>();

        for (ActionInputData a : input.getCommands()) {
            switch (a.getActionType()) {
                case Constants.COMMAND:
                    actions.add(new Command(a.getActionId(), users.getUser(a.getUsername()),
                            a.getType(), shows.getShow(a.getTitle()), a.getGrade(),
                            a.getSeasonNumber()));
                    break;
                case Constants.QUERY:
                    switch (a.getObjectType()) {
                        case Constants.ACTORS -> actions.add(
                                new ActorQueries(a.getActionId(), a.getNumber(),
                                a.getFilters(), a.getSortType(), a.getCriteria(), actors));
                        case Constants.MOVIES -> actions.add(
                                new ShowQueries<Movie>(a.getActionId(), a.getNumber(),
                                a.getFilters(), a.getSortType(), a.getCriteria(),
                                shows.getMovies(), users));
                        case Constants.SHOWS -> actions.add(
                                new ShowQueries<Serial>(a.getActionId(), a.getNumber(),
                                a.getFilters(), a.getSortType(), a.getCriteria(),
                                shows.getSerials(), users));
                        default -> actions.add(new UserQueries(
                                a.getActionId(), a.getNumber(),
                                a.getSortType(), a.getCriteria(), users));
                    }
                    break;
                default:
                    actions.add(new Recommendation(a.getActionId(),
                            users.getUser(a.getUsername()), shows, users,
                            a.getType(), a.getGenre()));
                    break;
            }
        }
    }

    /**
     * @return a list of actions
     */
    public ArrayList<Action> getActions() {
        return actions;
    }
}
