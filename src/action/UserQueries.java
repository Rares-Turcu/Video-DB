package action;

import common.Constants;
import fileio.Writer;
import org.json.simple.JSONObject;
import user.User;
import user.UserContainer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class UserQueries extends Queries {

    private final UserContainer users;
    private final String criteria;

    public UserQueries(final int actionId, final int number, final String sortType,
                       final String criteria, final UserContainer users) {
        super(actionId, number, sortType);
        this.users = users;
        this.criteria = criteria;
    }

    /**
     * @param fileWriter for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    @Override
    public JSONObject work(final Writer fileWriter) throws IOException {
        if (criteria.equals(Constants.NUM_RATINGS)) {
            return fileWriter.writeFile(super.getActionId(), null, numberOfRatings());
        }
        return null;
    }

    /**
     * @return a string used for output file
     */
    public String numberOfRatings() {
        ArrayList<User> sorted = new ArrayList<>();
        for (User u : users.getUsers()) {
            if (u.getNumberOfRatings() != 0) {
                sorted.add(u);
            }
        }

        int type = 1;
        if (getSortType().equals("desc")) {
            type = -1;
        }

        int finalType = type;
        sorted.sort(new Comparator<User>() {
            @Override
            public int compare(final User o1, final User o2) {
                if (o1.getNumberOfRatings() == o2.getNumberOfRatings()) {
                    return (o1.getUsername().compareTo(o2.getUsername())) * finalType;
                }
                return (o1.getNumberOfRatings() - o2.getNumberOfRatings()) * finalType;
            }
        });

        sorted = trimToNumber(sorted);

        return "Query result: " + sorted;
    }

    /**
     * @param sorted<User>
     * @return the first n shows used for output
     */
    public ArrayList<User> trimToNumber(final ArrayList<User> sorted) {
        if (!(getNumber() >= sorted.size() || getNumber() == 0)) {
            while (sorted.size() > getNumber()) {
                sorted.remove(sorted.size() - 1);
            }
        }

        return sorted;
    }
}
