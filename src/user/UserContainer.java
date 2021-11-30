package user;

import common.Constants;
import fileio.Input;
import fileio.UserInputData;

import java.util.ArrayList;

public class UserContainer {
    private final ArrayList<User> users;

    public UserContainer(final Input input) {
        this.users = new ArrayList<>();

        for (UserInputData u : input.getUsers()) {
            switch (u.getSubscriptionType()) {
                case Constants.BASIC -> users.add(new User(u.getUsername(),
                        u.getHistory(), u.getFavoriteMovies()));
                default -> users.add(new Premium(u.getUsername(),
                        u.getHistory(), u.getFavoriteMovies()));
            }
        }
    }

    /**
     * @return users
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * @param  username
     * @return a specific user
     */
    public User getUser(final String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }

        return null;
    }
}
