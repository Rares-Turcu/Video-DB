package action;

import common.Constants;
import fileio.Writer;
import org.json.simple.JSONObject;
import actor.Actor;
import actor.ActorContainer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static utils.Utils.stringToAwards;

public class ActorQueries extends Queries {

    private final ActorContainer actors;
    private final List<List<String>> filters;
    private final String criteria;

    public ActorQueries(final int actionId, final int number, final List<List<String>> filters,
                        final String sortType, final String criteria,
                        final ActorContainer actors) {
        super(actionId, number, sortType);
        this.actors = actors;
        this.filters = filters;
        this.criteria = criteria;
    }

    /**
     * @param fileWriter for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    @Override
    public JSONObject work(final Writer fileWriter) throws IOException {
        return switch (criteria) {
            case Constants.AVERAGE -> fileWriter.writeFile(super.getActionId(), null, average());
            case Constants.AWARDS -> fileWriter.writeFile(super.getActionId(), null, awards());
            case Constants.FILTER_DESCRIPTIONS -> fileWriter.writeFile(super.getActionId(),
                    null, filterDescription());
            default -> null;
        };
    }

    /**
     * @return a string used for output file
     */
    public String average() {
        ArrayList<Actor> sorted = new ArrayList<>();
        for (Actor a : actors.getActors()) {
            if (a.getAverage() != 0) {
                sorted.add(a);
            }
        }

        if (sorted.size() == 0 || sorted.size() == 1) {
            return "Query result: " + sorted;
        }

        int type = 1;
        if (getSortType().equals("desc")) {
            type = -1;
        }

        int finalType = type;
        sorted.sort(new Comparator<Actor>() {
            @Override
            public int compare(final Actor o1, final Actor o2) {
                if (o1.getAverage() == o2.getAverage()) {
                    return (o1.getName().compareTo(o2.getName())) * finalType;
                }
                if (o1.getAverage() < o2.getAverage()) {
                    return -1 * finalType;
                }
                return finalType;
            }
        });
        sorted = trimToNumber(sorted);

        return "Query result: " + sorted;
    }

    /**
     * @return a string used for output file
     */
    public String awards() {

        List<String> awards = filters.get(Constants.AWARDS_FILTER);
        ArrayList<Actor> sorted = new ArrayList<>();

        for (Actor a : actors.getActors()) {
            int check = 0;

            for (int i = 0; i < awards.size(); i++) {
                if (a.getAwards().containsKey(stringToAwards(awards.get(i)))) {
                    check++;
                }
            }
            if (check == awards.size()) {
                sorted.add(a);
            }
        }

        if (sorted.isEmpty() || sorted.size() == 1) {
            return "Query result: " + sorted.toString();
        }

        int type = 1;
        if (getSortType().equals("desc")) {
            type = -1;
        }

        int finalType = type;
        sorted.sort(new Comparator<Actor>() {
            @Override
            public int compare(final Actor o1, final Actor o2) {
                if (o1.getAwardsNumber() == o2.getAwardsNumber()) {
                    return (o1.getName().compareTo(o2.getName())) * finalType;
                }
                return (o1.getAwardsNumber() - o2.getAwardsNumber()) * finalType;
            }
        });
        sorted = trimToNumber(sorted);

        return "Query result: " + sorted;
    }

    /**
     * @return a string used for output file
     */
    public String filterDescription() {
        ArrayList<Actor> sorted = new ArrayList<>();

        for (Actor a : actors.getActors()) {
            List<String> words = new ArrayList<>();

            String desc = a.getDescription().toLowerCase();
            Pattern pattern = Pattern.compile("([A-Za-z]+)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(desc);

            for (String s : filters.get(2)) {
                words.add(s);
            }

            while (matcher.find()) {
                for (int j = 0; j < matcher.groupCount(); j++) {

                    for (int i = 0; i < words.size(); i++) {
                        if (words.get(i).equals(matcher.group(j))) {
                            words.remove(i);
                        }
                    }
                }
            }

            if (words.isEmpty()) {
                sorted.add(a);
            }
        }

        if (sorted.isEmpty() || sorted.size() == 1) {
            return "Query result: " + sorted.toString();
        }

        int type = 1;
        if (getSortType().equals("desc")) {
            type = -1;
        }

        int finalType = type;
        sorted.sort(new Comparator<Actor>() {
            @Override
            public int compare(final Actor o1, final Actor o2) {
                return (o1.getName().compareTo(o2.getName())) * finalType;
            }
        });

        sorted = trimToNumber(sorted);

        return "Query result: " + sorted;
    }

    /**
     * @param sorted<Actor>
     * @return the first n actors used for output
     */
    public ArrayList<Actor> trimToNumber(final ArrayList<Actor> sorted) {
        if (!(getNumber() >= sorted.size() || getNumber() == 0)) {
            while (sorted.size() > getNumber()) {
                sorted.remove(sorted.size() - 1);
            }
        }

        return sorted;
    }
}
