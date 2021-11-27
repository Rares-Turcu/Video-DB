package run.action;

import common.Constants;
import fileio.Writer;
import org.json.simple.JSONObject;
import run.user.UserContainer;
import run.video.Serial;
import run.video.Show;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ShowQueries<E extends Show> extends Queries {

    private final ArrayList<E> shows;
    private final UserContainer users;
    private final List<List<String>> filters;
    private final String criteria;

    public ShowQueries(int actionId, int number, List<List<String>> filters, String sort_type, String criteria, ArrayList<E> movies, UserContainer users) {
        super(actionId, number, sort_type);
        this.shows = movies;
        this.users = users;
        this.filters = filters;
        this.criteria = criteria;
    }

    @Override
    public JSONObject work(Writer fileWriter) throws IOException {
        if (criteria.equals(Constants.FAVORITE)) {
            return fileWriter.writeFile(super.getActionId(), null, favorite());
        }
        if (criteria.equals(Constants.LONGEST)) {
            return fileWriter.writeFile(super.getActionId(), null, longest());
        }
        if (criteria.equals(Constants.MOST_VIEWED)) {
            return fileWriter.writeFile(super.getActionId(), null, mostViewed());
        }
        if (criteria.equals(Constants.RATINGS)) {
            return fileWriter.writeFile(super.getActionId(), null, rating());
        }
        return null;
    }

    public String favorite() {

        ArrayList<E> sorted = getList();

        for (E m : sorted) {
            m.tallyFavorite(users);
        }

        sorted = trimBadDataFavorite(sorted);

        if(sorted.size() == 0 || sorted.size() == 1) {
            return "Query result: " + sorted;
        }

        int type = 1;
        if (getSort_type().equals("desc")) {
            type = -1;
        }

        int finalType = type;
        sorted.sort(new Comparator<E>() {
            @Override
            public int compare(E o1, E o2) {
                if (o1.getFavourites() == o2.getFavourites()) {
                    return (o1.getTitle().compareTo(o2.getTitle())) * finalType;
                }
                return (o1.getFavourites() - o2.getFavourites()) * finalType;
            }
        });

        sorted = TrimToNumber(sorted);

        return "Query result: " + sorted;
    }

    public String longest() {

        ArrayList<E> sorted = getList();

        if(sorted.size() == 0 || sorted.size() == 1) {
            return "Query result: " + sorted;
        }

        int type = 1;
        if (getSort_type().equals("desc")) {
            type = -1;
        }

        int finalType = type;
        sorted.sort(new Comparator<E>() {
            @Override
            public int compare(E o1, E o2) {
                return (o1.getDuration() - o2.getDuration()) * finalType;
            }
        });

        sorted = TrimToNumber(sorted);

        return "Query result: " + sorted;
    }

    public String mostViewed() {

        ArrayList<E> sorted = getList();

        for (E m : sorted) {
            m.tallyViews(users);
        }

        sorted = trimBadDataView(sorted);

        if(sorted.size() == 0 || sorted.size() == 1) {
            return "Query result: " + sorted;
        }

        int type = 1;
        if (getSort_type().equals("desc")) {
            type = -1;
        }

        int finalType = type;
        sorted.sort(new Comparator<E>() {
            @Override
            public int compare(E o1, E o2) {
                return (o1.getViews() - o2.getViews()) * finalType;
            }
        });

        sorted = TrimToNumber(sorted);

        return "Query result: " + sorted;
    }

    public String rating() {
        ArrayList<E> sorted = getList();

        sorted = trimBadDataRate(sorted);

        if(sorted.size() == 0 || sorted.size() == 1) {
            return "Query result: " + sorted;
        }

        int type = 1;
        if (getSort_type().equals("desc")) {
            type = -1;
        }

        int finalType = type;
        sorted.sort(new Comparator<E>() {
            @Override
            public int compare(E o1, E o2) {
                return ((int) (o1.getNote() - o2.getNote())) * finalType;
            }
        });

        sorted = TrimToNumber(sorted);

        return "Query result: " + sorted;
    }

    public ArrayList<E> getList() {

        System.out.println(filters);
        for(int i = 0; i < filters.size(); i++) {
            List<String> filter = filters.get(i);
            System.out.println(filter);
        }

        ArrayList<E> sorted = new ArrayList<>();

        for (E m : shows) {
            sorted.add(m);
        }

        List<String> years = filters.get(0);
        List<String> genre = filters.get(1);


        if(!(years.isEmpty() || (years.size() == 1 && years.get(0) == null))) {
            sorted  = yearFilter(sorted, years);
        }

        if(!(genre.isEmpty() || (genre.size() == 1 && genre.get(0) == null))) {
            sorted = genreFilter(sorted, genre);
        }

        return sorted;
    }

    public ArrayList<E> yearFilter(ArrayList<E> sorted, List<String> years){
        System.out.println(sorted);
        ArrayList<E> newSorted = new ArrayList<>();

        for (E m : sorted) {
            for (String s : years) {
                if(s != null) {
                    if (m.getYear() == Integer.parseInt(s)) {
                        newSorted.add(m);
                        break;
                    }
                }
            }
        }

        System.out.println("Movies in " + years);
        System.out.println(newSorted);
        System.out.println("\n\n\n");
        return newSorted;
    }

    public ArrayList<E> genreFilter(ArrayList<E> sorted, List<String> genre){
        System.out.println(sorted);
        ArrayList<E> newSorted = new ArrayList<>();

        for (E m : sorted) {
            for (String s : genre) {
                if(s != null) {
                    if (m.getGenres().contains(s)) {
                        newSorted.add(m);
                        break;
                    }
                }
            }
        }

        System.out.println("Movies with " + genre);
        System.out.println(newSorted);
        System.out.println("\n\n\n");
        return newSorted;
    }

    public ArrayList<E> trimBadDataFavorite(ArrayList<E> sorted) {
        ArrayList<E> newSorted = new ArrayList<>();

        for (E m : sorted) {
            if (m.getFavourites() != 0) {
                newSorted.add(m);
            }
        }
        return newSorted;
    }

    public ArrayList<E> trimBadDataView(ArrayList<E> sorted) {
        ArrayList<E> newSorted = new ArrayList<>();

        for (E m : sorted) {
            if (m.getViews() != 0) {
                newSorted.add(m);
            }
        }
        return newSorted;
    }

    public ArrayList<E> trimBadDataRate(ArrayList<E> sorted) {
        ArrayList<E> newSorted = new ArrayList<>();

        for (E m : sorted) {
            if (m.getNote() != 0) {
                newSorted.add(m);
            }
        }
        return newSorted;
    }

    public ArrayList<E> TrimToNumber(ArrayList<E> sorted){
        if (!(getNumber() >= sorted.size() || getNumber() == 0)) {
            while (sorted.size() > getNumber()) {
                sorted.remove(sorted.size() - 1);
            }
        }

        return sorted;
    }
}
