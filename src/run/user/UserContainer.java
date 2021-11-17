package run.user;

import common.Constants;
import fileio.Input;
import fileio.UserInputData;

import java.util.ArrayList;

public class UserContainer {
    private final ArrayList<User> users;

    public UserContainer(Input input) {
        this.users = new ArrayList<>();

        for (UserInputData u : input.getUsers()) {
            if (u.getSubscriptionType().equals(Constants.BASIC)) {
                users.add(new User(u.getUsername(), u.getHistory(), u.getFavoriteMovies()));
            }
            if (u.getSubscriptionType().equals(Constants.PREMIUM)) {
                users.add(new Premium(u.getUsername(), u.getHistory(), u.getFavoriteMovies()));
            }
        }
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public User getUser(String username) {
        for (User u : users) {
            if(u.getUsername().equals(username)) {
                return u;
            }
        }

        return null;
    }
}
