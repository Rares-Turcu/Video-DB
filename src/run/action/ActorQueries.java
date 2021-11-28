package run.action;

import actor.ActorsAwards;
import common.Constants;
import fileio.Writer;
import org.json.simple.JSONObject;
import run.actor.Actor;
import run.actor.ActorContainer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActorQueries extends Queries {

    private final ActorContainer actors;
    private final List<List<String>> filters;
    private final String criteria;

    public ActorQueries(int actionId, int number, List<List<String>> filters, String sort_type, String criteria, ActorContainer actors) {
        super(actionId, number, sort_type);
        this.actors = actors;
        this.filters = filters;
        this.criteria = criteria;
    }

    @Override
    public JSONObject work(Writer fileWriter) throws IOException {
        if (criteria.equals(Constants.AVERAGE)) {
            return fileWriter.writeFile(super.getActionId(), null, average());
        }
        if (criteria.equals(Constants.AWARDS)) {
            return fileWriter.writeFile(super.getActionId(), null, awards());
        }
        if (criteria.equals(Constants.FILTER_DESCRIPTIONS)) {
            return fileWriter.writeFile(super.getActionId(), null, filterDescription());
        }
        return null;
    }

    public String average() {
        ArrayList<Actor> sorted = new ArrayList<>();
        for (Actor a : actors.getActors()) {
            if (a.getAverage() != 0) {
                sorted.add(a);
            }
        }

        if(sorted.size() == 0 || sorted.size() == 1) {
            return "Query result: " + sorted;
        }

        int type = 1;
        if (getSort_type().equals("desc")) {
            type = -1;
        }

        int finalType = type;
        sorted.sort(new Comparator<Actor>() {
            @Override
            public int compare(Actor o1, Actor o2) {
                if (o1.getAverage() == o2.getAverage()) {
                    return (o1.getName().compareTo(o2.getName())) * finalType;
                }
                if (o1.getAverage() < o2.getAverage()) {
                    return -1 * finalType;
                }
                return finalType;
            }
        });
        sorted = TrimToNumber(sorted);

        return "Query result: " + sorted;
    }

    public String awards() {
        List<String> awards = filters.get(3);
        ArrayList<Actor> sorted = new ArrayList<>();

        for (Actor a : actors.getActors()) {
            int check = 0;


            for (int i = 0 ; i < awards.size(); i++) {
                if (a.getAwards().containsKey(ActorsAwards.valueOf(awards.get(i)))) {
                    check++;
                }
            }
            if(check == awards.size()) {
                sorted.add(a);
            }
        }
        if(sorted.isEmpty() || sorted.size() == 1) {
            return "Query result: " + sorted.toString();
        }

        int type = 1;
        if (getSort_type().equals("desc")) {
            type = -1;
        }

        int finalType = type;
        sorted.sort(new Comparator<Actor>() {
            @Override
            public int compare(Actor o1, Actor o2) {
                if (o1.getAwardsNumber() == o2.getAwardsNumber()) {
                    return (o1.getName().compareTo(o2.getName())) * finalType;
                }
                return (o1.getAwardsNumber() - o2.getAwardsNumber()) * finalType;
            }
        });
        sorted = TrimToNumber(sorted);

        return "Query result: " + sorted;
    }

    public String filterDescription() {
        ArrayList<Actor> sorted = new ArrayList<>();

        for (Actor a : actors.getActors()) {
            List<String> words = new ArrayList<>();

            String desc = a.getDescription().toLowerCase();
            Pattern pattern = Pattern.compile("([A-Za-z]+)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(desc);

            for(String s : filters.get(2)) {
                words.add(s);
            }

            while(matcher.find()) {
                for (int j = 0; j < matcher.groupCount(); j++) {

                    for (int i = 0 ; i < words.size(); i++) {
                        if(words.get(i).equals(matcher.group(j))) {
                            words.remove(i);
                        }
                    }
                }
            }

            if(words.isEmpty()) {
                sorted.add(a);
            }
        }

        if(sorted.isEmpty() || sorted.size() == 1) {
            return "Query result: " + sorted.toString();
        }

        int type = 1;
        if (getSort_type().equals("desc")) {
            type = -1;
        }

        int finalType = type;
        sorted.sort(new Comparator<Actor>() {
            @Override
            public int compare(Actor o1, Actor o2) {
                return (o1.getName().compareTo(o2.getName())) * finalType;
            }
        });

        sorted = TrimToNumber(sorted);

        return "Query result: " + sorted;
    }

    public ArrayList<Actor> TrimToNumber(ArrayList<Actor> sorted){
        if (!(getNumber() >= sorted.size() || getNumber() == 0)) {
            while (sorted.size() > getNumber()) {
                sorted.remove(sorted.size() - 1);
            }
        }

        return sorted;
    }

}
