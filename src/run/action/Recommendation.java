package run.action;

import common.Constants;
import fileio.Writer;
import org.json.simple.JSONObject;
import run.user.User;
import run.user.UserContainer;
import run.video.Movie;
import run.video.Serial;
import run.video.Show;
import run.video.ShowContainer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Recommendation extends Action {

    private final User user;
    private final ShowContainer shows;
    private final UserContainer users;
    private final String type;
    private final String genre;

    public Recommendation(final int actionId, User user, ShowContainer shows, UserContainer users, String type, String genre) {
        super(actionId);
        this.user = user;
        this.shows = shows;
        this.users = users;
        this.type = type;
        this.genre = genre;
    }

    @Override
    public JSONObject work(Writer fileWriter) throws IOException {
        if (type.equals(Constants.STANDARD)) {
            return fileWriter.writeFile(super.getActionId(), null, standard());
        }
        if (type.equals(Constants.BEST_UNSEEN)) {
            return fileWriter.writeFile(super.getActionId(), null, bestUnseen());
        }
        if (type.equals(Constants.POPULAR)) {
            return fileWriter.writeFile(super.getActionId(), null, popular());
        }
        if (type.equals(Constants.FAVORITE)) {
            return fileWriter.writeFile(super.getActionId(), null, favorite());
        }
        if (type.equals(Constants.SEARCH)) {
            return fileWriter.writeFile(super.getActionId(), null, search());
        }
        return null;
    }

    public String standard() {
        for(Movie m : shows.getMovies()) {
            if(!user.getHistory().containsKey(m.getTitle())) {
                return "StandardRecommendation result: " + m.getTitle();
            }
        }

        for(Serial s : shows.getSerials()) {
            if(!user.getHistory().containsKey(s.getTitle())) {
                return "StandardRecommendation result: " + s.getTitle();
            }
        }

        return "StandardRecommendation cannot be applied!";
    }

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
            public int compare(Show o1, Show o2) {
                return ((int) (o1.getNote() - o2.getNote())) * (-1);
            }
        });

        return "BestRatedUnseenRecommendation result: " + unseenShows.get(0).getTitle();
    }

    public String popular() {
        ArrayList<Show> unseenShows = getList();

        if(unseenShows.isEmpty()) {
            return "PopularRecommendation cannot be applied!";
        }

        HashMap<String, Integer> genrePopularity = getGenrePopularity();

        String popularGenre;
        String result;

        do {
            popularGenre = getPopularGenre(genrePopularity);
            result = user.recommendationPopular(unseenShows, popularGenre);
        }
        while(result.equals("PopularRecommendation cannot be applied!") && !genrePopularity.isEmpty());

        return result;
    }

    public String favorite() {
        ArrayList<Show> unseenShows = getList();

        for (Show m : unseenShows) {
            m.tallyFavorite(users);
        }

        if(unseenShows.isEmpty()) {
            return "FavoriteRecommendation cannot be applied!";
        }

        return user.recommendationFavorite(unseenShows);
    }

    public String search() {
        ArrayList<Show> unseenShows = getList();

        for (int i = 0; i < unseenShows.size(); i++) {
            if (!(unseenShows.get(i).getGenres().contains(genre))) {
                unseenShows.remove(i);
                i--;
            }
        }

        if(unseenShows.isEmpty()) {
            return "SearchRecommendation cannot be applied!";
        }

        return user.recommendationSearch(unseenShows);
    }

    public ArrayList<Show> getList() {
        ArrayList<Show> unseenShows = new ArrayList<>();

        for(Movie m : shows.getMovies()) {
            if(!user.getHistory().containsKey(m.getTitle())) {
                unseenShows.add(m);
            }
        }

        for(Serial s : shows.getSerials()) {
            if(!user.getHistory().containsKey(s.getTitle())) {
                unseenShows.add(s);
            }
        }

        return unseenShows;
    }

    public HashMap<String, Integer> getGenrePopularity() {
        HashMap<String, Integer> genrePopularity = new HashMap<>();
        ArrayList<Show> showList = new ArrayList<>();

        for(Movie m : shows.getMovies()) {
            showList.add(m);
        }

        for(Serial s : shows.getSerials()) {
            showList.add(s);
        }

        for(Show s : showList) {
            for (int i = 0; i < s.getGenres().size(); i++) {

                if(genrePopularity.containsKey(s.getGenres().get(i))) {
                    genrePopularity.put(s.getGenres().get(i), genrePopularity.get(s.getGenres().get(i)) + 1);
                }
                else {
                    genrePopularity.put(s.getGenres().get(i), 1);
                }
            }
        }

        return genrePopularity;
    }

    public String getPopularGenre(HashMap<String, Integer> genrePopularity) {
        String maxGenre = null;
        int max = 0;

        for (String s : genrePopularity.keySet()) {
            if (genrePopularity.get(s) > max) {
                maxGenre = s;
                max = genrePopularity.get(s);
            }
        }

        genrePopularity.remove(maxGenre);

        return maxGenre;
    }
}
