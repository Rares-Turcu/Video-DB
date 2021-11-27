package run.action;

import common.Constants;
import fileio.Writer;
import org.json.simple.JSONObject;
import run.actor.Actor;
import run.user.User;
import run.user.UserContainer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class UserQueries extends Queries {

    private final UserContainer users;
    private final String criteria;

    public UserQueries(int actionId, int number, String sort_type, String criteria, UserContainer users) {
        super(actionId, number, sort_type);
        this.users = users;
        this.criteria = criteria;
    }

    @Override
    public JSONObject work(Writer fileWriter) throws IOException {
        if (criteria.equals(Constants.NUM_RATINGS)) {
            return fileWriter.writeFile(super.getActionId(), null, numberOfRatings());
        }
        return null;
    }

    public String numberOfRatings() {
        ArrayList<User> sorted = new ArrayList<>();
        for (User u : users.getUsers()) {
            if (u.getNumberOfRatings() != 0) {
                sorted.add(u);
            }
        }

        int type = 1;
        if (getSort_type().equals("desc")) {
            type = -1;
        }

        int finalType = type;
        sorted.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                if (o1.getNumberOfRatings() == o2.getNumberOfRatings()) {
                    return (o1.getUsername().compareTo(o2.getUsername()));
                }
                return ((int) (o1.getNumberOfRatings() - o2.getNumberOfRatings())) * finalType;
            }
        });

        sorted = TrimToNumber(sorted);

        return "Query result: " + sorted;
    }

    public ArrayList<User> TrimToNumber(ArrayList<User> sorted){
        if (!(getNumber() >= sorted.size() || getNumber() == 0)) {
            while (sorted.size() > getNumber()) {
                sorted.remove(sorted.size() - 1);
            }
        }

        return sorted;
    }
}
