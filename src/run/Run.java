package run;

import fileio.Input;
import fileio.Writer;
import org.json.simple.JSONArray;

import action.Action;
import action.ActionFactory;
import actor.ActorContainer;
import user.UserContainer;
import entertainment.ShowContainer;
import java.io.IOException;
import java.util.ArrayList;

public class Run {
    private final UserContainer users;
    private final ShowContainer shows;
    private final ActorContainer actors;
    private final ArrayList<Action> actions;
    private final JSONArray arrayResult;
    private final Writer fileWriter;

    public Run(final Input input, final JSONArray arrayResult, final Writer fileWriter) {
        this.fileWriter = fileWriter;
        this.arrayResult = arrayResult;

        this.users = new UserContainer(input);
        this.actors = new ActorContainer(input);
        this.shows = new ShowContainer(input, actors);

        this.actions = (new ActionFactory(input, users, shows, actors)).getActions();
    }

    /**
     * @throws IOException in case of exceptions to reading / writing
     */
    public void run() throws IOException {
        while (!actions.isEmpty()) {
            arrayResult.add(actions.get(0).work(fileWriter));
            actions.remove(0);
        }
    }
}
