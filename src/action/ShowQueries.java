package action;

import common.Constants;
import fileio.Writer;
import org.json.simple.JSONObject;
import user.UserContainer;
import entertainment.Show;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static utils.Utils.stringToGenre;

public class ShowQueries<E extends Show> extends Queries {

    private final ArrayList<E> shows;
    private final UserContainer users;
    private final List<List<String>> filters;
    private final String criteria;

    public ShowQueries(final int actionId, final int number, final List<List<String>> filters,
                       final String sortType, final String criteria, final ArrayList<E> movies,
                       final UserContainer users) {
        super(actionId, number, sortType);
        this.shows = movies;
        this.users = users;
        this.filters = filters;
        this.criteria = criteria;
    }

    /**
     * @param fileWriter for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    @Override
    public JSONObject work(final Writer fileWriter) throws IOException {
        switch (criteria) {
            case Constants.FAVORITE:
                return fileWriter.writeFile(super.getActionId(), null, favorite());
            case Constants.LONGEST:
                return fileWriter.writeFile(super.getActionId(), null, longest());
            case Constants.MOST_VIEWED:
                return fileWriter.writeFile(super.getActionId(), null, mostViewed());
            case Constants.RATINGS:
                return fileWriter.writeFile(super.getActionId(), null, rating());
            default:
                return null;
        }
    }

    /**
     * @return a string used for output file
     */
    public String favorite() {
        ArrayList<E> sorted = getList();

        for (E m : sorted) {
            m.tallyFavorite(users);
        }

        sorted = trimBadDataFavorite(sorted);

        if (sorted.size() == 0 || sorted.size() == 1) {
            return "Query result: " + sorted;
        }

        int type = 1;
        if (getSortType().equals("desc")) {
            type = -1;
        }

        int finalType = type;
        sorted.sort(new Comparator<E>() {
            @Override
            public int compare(final E o1, final E o2) {
                if (o1.getFavourites() == o2.getFavourites()) {
                    return (o1.getTitle().compareTo(o2.getTitle())) * finalType;
                }
                return (o1.getFavourites() - o2.getFavourites()) * finalType;
            }
        });

        sorted = trimToNumber(sorted);

        return "Query result: " + sorted;
    }

    /**
     * @return a string used for output file
     */
    public String longest() {
        ArrayList<E> sorted = getList();

        if (sorted.size() == 0 || sorted.size() == 1) {
            return "Query result: " + sorted;
        }

        int type = 1;
        if (getSortType().equals("desc")) {
            type = -1;
        }

        int finalType = type;
        sorted.sort(new Comparator<E>() {
            @Override
            public int compare(final E o1, final E o2) {
                if (o1.getDuration() == o2.getDuration()) {
                    return o1.getTitle().compareTo(o2.getTitle()) * finalType;
                }
                return (o1.getDuration() - o2.getDuration()) * finalType;
            }
        });

        sorted = trimToNumber(sorted);

        return "Query result: " + sorted;
    }

    /**
     * @return a string used for output file
     */
    public String mostViewed() {
        ArrayList<E> sorted = getList();

        for (E m : sorted) {
            m.tallyViews(users);
        }

        sorted = trimBadDataView(sorted);

        if (sorted.size() == 0 || sorted.size() == 1) {
            return "Query result: " + sorted;
        }

        int type = 1;
        if (getSortType().equals("desc")) {
            type = -1;
        }

        int finalType = type;
        sorted.sort(new Comparator<E>() {
            @Override
            public int compare(final E o1, final E o2) {
                if (o1.getViews() == o2.getViews()) {
                    return o1.getTitle().compareTo(o2.getTitle()) * finalType;
                }
                return (o1.getViews() - o2.getViews()) * finalType;
            }
        });

        sorted = trimToNumber(sorted);

        return "Query result: " + sorted;
    }

    /**
     * @return a string used for output file
     */
    public String rating() {
        ArrayList<E> sorted = getList();

        sorted = trimBadDataRate(sorted);

        if (sorted.size() == 0 || sorted.size() == 1) {
            return "Query result: " + sorted;
        }

        int type = 1;
        if (getSortType().equals("desc")) {
            type = -1;
        }

        int finalType = type;
        sorted.sort(new Comparator<E>() {
            @Override
            public int compare(final E o1, final E o2) {
                if (o1.getNote() < o2.getNote()) {
                    return -1 * finalType;
                }
                if (o1.getNote() > o2.getNote()) {
                    return finalType;
                }
                return o1.getTitle().compareTo(o2.getTitle()) * finalType;
            }
        });

        sorted = trimToNumber(sorted);

        return "Query result: " + sorted;
    }

    /**
     * @return a list of all the shows that fit the year and genre filters
     */
    public ArrayList<E> getList() {
        ArrayList<E> sorted = new ArrayList<>();

        for (E m : shows) {
            sorted.add(m);
        }

        List<String> years = filters.get(0);
        List<String> genre = filters.get(1);


        if (!(years.isEmpty() || (years.size() == 1 && years.get(0) == null))) {
            sorted  = yearFilter(sorted, years);
        }

        if (!(genre.isEmpty() || (genre.size() == 1 && genre.get(0) == null))) {
            sorted = genreFilter(sorted, genre);
        }

        return sorted;
    }

    /**
     * @param sorted<E>
     * @param years<String>
     * @return a list of every show from a given year
     */
    public ArrayList<E> yearFilter(final ArrayList<E> sorted, final List<String> years) {
        ArrayList<E> newSorted = new ArrayList<>();

        for (E m : sorted) {
            for (String s : years) {
                if (s != null) {
                    if (m.getYear() == Integer.parseInt(s)) {
                        newSorted.add(m);
                        break;
                    }
                }
            }
        }

        return newSorted;
    }

    /**
     * @param sorted<E>
     * @param genre<String>
     * @return a list of every show from a given genre
     */
    public ArrayList<E> genreFilter(final ArrayList<E> sorted, final List<String> genre) {
        ArrayList<E> newSorted = new ArrayList<>();

        for (E m : sorted) {
            for (String s : genre) {
                if (s != null) {
                    if (m.getGenres().contains(stringToGenre(s))) {
                        newSorted.add(m);
                        break;
                    }
                }
            }
        }

        return newSorted;
    }

    /**
     * @param sorted<E>
     * @return a list that removes every show that don't appear in any favorite list
     */
    public ArrayList<E> trimBadDataFavorite(final ArrayList<E> sorted) {
        ArrayList<E> newSorted = new ArrayList<>();

        for (E m : sorted) {
            if (m.getFavourites() != 0) {
                newSorted.add(m);
            }
        }
        return newSorted;
    }

    /**
     * @param sorted<E>
     * @return a list that removes every show that wasn't viewed yet
     */
    public ArrayList<E> trimBadDataView(final ArrayList<E> sorted) {
        ArrayList<E> newSorted = new ArrayList<>();

        for (E m : sorted) {
            if (m.getViews() != 0) {
                newSorted.add(m);
            }
        }
        return newSorted;
    }

    /**
     * @param sorted<E>
     * @return a list that removes every show that wasn't reviewed yet
     */
    public ArrayList<E> trimBadDataRate(final ArrayList<E> sorted) {
        ArrayList<E> newSorted = new ArrayList<>();

        for (E m : sorted) {
            if (m.getNote() != 0) {
                newSorted.add(m);
            }
        }
        return newSorted;
    }

    /**
     * @param sorted<E>
     * @return the first n shows used for output
     */
    public ArrayList<E> trimToNumber(final ArrayList<E> sorted) {
        if (!(getNumber() >= sorted.size() || getNumber() == 0)) {
            while (sorted.size() > getNumber()) {
                sorted.remove(sorted.size() - 1);
            }
        }

        return sorted;
    }
}
