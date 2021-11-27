package run;

import common.Constants;
import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import org.json.simple.JSONArray;
import run.action.*;
import run.actor.ActorContainer;
import run.user.User;
import run.user.UserContainer;
import run.video.Movie;
import run.video.Serial;
import run.video.ShowContainer;

import java.io.IOException;
import java.util.ArrayList;

public class Run {
    private final UserContainer users;
    private final ShowContainer shows;
    private final ActorContainer actors;

    private final ArrayList<Action> actions;
    private final JSONArray arrayResult;
    private final Writer fileWriter;

    public Run(Input input, JSONArray arrayResult, Writer fileWriter) {
        this.fileWriter = fileWriter;
        this.arrayResult = arrayResult;

        this.users = new UserContainer(input);
        this.actors = new ActorContainer(input);
        this.shows = new ShowContainer(input, actors);


        this.actions = new ArrayList<>();

        for (ActionInputData a : input.getCommands()) {
            if (a.getActionType().equals(Constants.COMMAND)) {
                actions.add(new Command(a.getActionId(), users.getUser(a.getUsername()), a.getType(), shows.getShow(a.getTitle()), a.getGrade(), a.getSeasonNumber()));
            }
            if (a.getActionType().equals(Constants.QUERY)) {
                if(a.getObjectType().equals(Constants.ACTORS)) {
                    actions.add(new ActorQueries(a.getActionId(), a.getNumber(), a.getFilters(), a.getSortType(), a.getCriteria(), actors));
                }
                if(a.getObjectType().equals(Constants.MOVIES)) {
                    actions.add(new ShowQueries<Movie>(a.getActionId(), a.getNumber(), a.getFilters(), a.getSortType(), a.getCriteria(), shows.getMovies(), users));
                }
                if(a.getObjectType().equals(Constants.SHOWS)) {
                    actions.add(new ShowQueries<Serial>(a.getActionId(), a.getNumber(), a.getFilters(), a.getSortType(), a.getCriteria(), shows.getSerials(), users));
                }
                if(a.getObjectType().equals(Constants.USERS)) {
                    actions.add(new UserQueries(a.getActionId(), a.getNumber(), a.getSortType(), a.getCriteria(), users));
                }
            }
        }


    }

    public void run() throws IOException {

        while(!actions.isEmpty()) {
            actions.toString();
            arrayResult.add(actions.get(0).work(fileWriter));
            actions.remove(0);
        }
    }

    public void testUser() {
        for (User u : users.getUsers()) {
            System.out.println(u.toString());
        }
    }

    public void testCommand() {
        for (Action a : actions) {
            System.out.println(a.toString());
        }
    }

}
