package action;

import common.Constants;
import entertainment.Genre;
import fileio.Writer;
import org.json.simple.JSONObject;
import user.User;
import user.UserContainer;
import entertainment.Movie;
import entertainment.Serial;
import entertainment.Show;
import entertainment.ShowContainer;
import visitor.FavoriteVisitor;
import visitor.PopularVisitor;
import visitor.SearchVisitor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import static utils.Utils.stringToGenre;

public class Recommendation extends Action {

    private final User user;
    private final ShowContainer shows;
    private final UserContainer users;
    private final String type;
    private final String genre;

    public Recommendation(final int actionId, final User user, final ShowContainer shows,
                          final UserContainer users, final String type, final String genre) {
        super(actionId);
        this.user = user;
        this.shows = shows;
        this.users = users;
        this.type = type;
        this.genre = genre;
    }

    /**
     * @param fileWriter for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    @Override
    public JSONObject work(final Writer fileWriter) throws IOException {
        switch (type) {
            case Constants.STANDARD:
                return fileWriter.writeFile(super.getActionId(), null, standard());
            case Constants.BEST_UNSEEN:
                return fileWriter.writeFile(super.getActionId(), null, bestUnseen());
            case Constants.POPULAR:
                return fileWriter.writeFile(super.getActionId(), null, popular());
            case Constants.FAVORITE:
                return fileWriter.writeFile(super.getActionId(), null, favorite());
            case Constants.SEARCH:
                return fileWriter.writeFile(super.getActionId(), null, search());
            default:
                return null;
        }
    }

    /**
     * @return a string used for output file
     */
    public String standard() {
        for (Movie m : shows.getMovies()) {
            if (!user.getHistory().containsKey(m.getTitle())) {
                return "StandardRecommendation result: " + m.getTitle();
            }
        }

        for (Serial s : shows.getSerials()) {
            if (!user.getHistory().containsKey(s.getTitle())) {
                return "StandardRecommendation result: " + s.getTitle();
            }
        }

        return "StandardRecommendation cannot be applied!";
    }

    /**
     * @return a string used for output file
     */
    public String bestUnseen() {
        ArrayList<Show> unseenShows = getList();

        if (unseenShows.isEmpty()) {
            return "BestRatedUnseenRecommendation cannot be applied!";
        }

        if (unseenShows.size() == 1) {
            return "BestRatedUnseenRecommendation result: " + unseenShows.get(0).getTitle();
        }

        unseenShows.sort(new Comparator<Show>() {
            @Override
            public int compare(final Show o1, final Show o2) {
                return ((int) (o1.getNote() - o2.getNote())) * (-1);
            }
        });

        return "BestRatedUnseenRecommendation result: " + unseenShows.get(0).getTitle();
    }

    /**
     * @return a string used for output file
     */
    public String popular() {
        ArrayList<Show> unseenShows = getList();

        if (unseenShows.isEmpty()) {
            return "PopularRecommendation cannot be applied!";
        }

        HashMap<Genre, Integer> genrePopularity = getGenrePopularity();

        Genre popularGenre;
        String result;

        do {
            popularGenre = getPopularGenre(genrePopularity);
            PopularVisitor popularVisitor = new PopularVisitor(unseenShows, popularGenre);

            user.accept(popularVisitor);

            result = popularVisitor.getResult();
        }
        while (result == null && !genrePopularity.isEmpty());

        if (result == null) {
            return "PopularRecommendation cannot be applied!";
        }
        return result;
    }

    /**
     * @return a string used for output file
     */
    public String favorite() {
        ArrayList<Show> unseenShows = getList();

        for (Show m : unseenShows) {
            m.tallyFavorite(users);
        }

        if (unseenShows.isEmpty()) {
            return "FavoriteRecommendation cannot be applied!";
        }

        FavoriteVisitor favoriteVisitor = new FavoriteVisitor(unseenShows);

        user.accept(favoriteVisitor);

        return favoriteVisitor.getResult();
    }

    /**
     * @return a string used for output file
     */
    public String search() {
        ArrayList<Show> unseenShows = getList();

        for (int i = 0; i < unseenShows.size(); i++) {
            if (!(unseenShows.get(i).getGenres().contains(stringToGenre(genre)))) {
                unseenShows.remove(i);
                i--;
            }
        }

        if (unseenShows.isEmpty()) {
            return "SearchRecommendation cannot be applied!";
        }

        SearchVisitor searchVisitor = new SearchVisitor(unseenShows);

        user.accept(searchVisitor);

        return searchVisitor.getResult();
    }

    /**
     * @return a list of all the shows in order of apparition
     */
    public ArrayList<Show> getList() {
        ArrayList<Show> unseenShows = new ArrayList<>();

        for (Movie m : shows.getMovies()) {
            if (!user.getHistory().containsKey(m.getTitle())) {
                unseenShows.add(m);
            }
        }

        for (Serial s : shows.getSerials()) {
            if (!user.getHistory().containsKey(s.getTitle())) {
                unseenShows.add(s);
            }
        }

        return unseenShows;
    }

    /**
     * @return a HashMap that contains the genre of every show in
     * the database with an integer representing the number shows in with that genre
     */
    public HashMap<Genre, Integer> getGenrePopularity() {
        HashMap<Genre, Integer> genrePopularity = new HashMap<>();
        ArrayList<Show> showList = new ArrayList<>();

        for (Movie m : shows.getMovies()) {
            showList.add(m);
        }

        for (Serial s : shows.getSerials()) {
            showList.add(s);
        }

        for (Show s : showList) {
            for (int i = 0; i < s.getGenres().size(); i++) {

                if (genrePopularity.containsKey(s.getGenres().get(i))) {
                    genrePopularity.put(s.getGenres().get(i),
                            genrePopularity.get(s.getGenres().get(i)) + 1);
                } else {
                    genrePopularity.put(s.getGenres().get(i), 1);
                }
            }
        }

        return genrePopularity;
    }

    /**
     * @param genrePopularity
     * @return the most popular genre remaining in the genrePopularity hash map
     */
    public Genre getPopularGenre(final HashMap<Genre, Integer> genrePopularity) {
        Genre maxGenre = null;
        int max = 0;

        for (Genre s : genrePopularity.keySet()) {
            if (genrePopularity.get(s) > max) {
                maxGenre = s;
                max = genrePopularity.get(s);
            }
        }

        genrePopularity.remove(maxGenre);

        return maxGenre;
    }
}
